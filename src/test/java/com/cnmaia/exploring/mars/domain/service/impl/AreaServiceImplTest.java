package com.cnmaia.exploring.mars.domain.service.impl;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cnmaia.exploring.mars.domain.exception.HoverCollisionException;
import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.model.Instruction;
import com.cnmaia.exploring.mars.domain.service.AreaService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cmaia on 8/27/17.
 */
public class AreaServiceImplTest {

    private static AreaService areaService;

    @BeforeClass
    public static void setup() {
        areaService = new AreaServiceImpl();
    }

    @Test
    public void testDeploySingleHover() {
        // Given
        Coordinate hoverCoordinates = new Coordinate(0, 0);
        Area area = new Area(new Coordinate(1, 1));
        Hover hover = new Hover("Curiosity", hoverCoordinates, Direction.NORTH);

        // When
        Area newAreaState = areaService.deployHover(area, hover);

        // Then
        assertNotNull(newAreaState);
        assertEquals(newAreaState.getHovers().size(), 1);
        assertEquals(newAreaState.getHovers().stream().findFirst().get().getCurrentLocation(), hoverCoordinates);
    }

    @Test
    public void testDeployMultiplesHovers() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);
        Hover opportunity = new Hover("Opportunity", new Coordinate(1, 1), Direction.NORTH);
        Hover spirit = new Hover("Spirit", new Coordinate(2, 2), Direction.NORTH);
        List<Hover> hovers = Arrays.asList(curiosity, opportunity, spirit);

        // When
        Area newAreaState = areaService.deployHovers(area, new HashSet<>(hovers));

        // Then
        assertNotNull(newAreaState);
        assertEquals(newAreaState.getHovers().size(), 3);

        List<String> names = Arrays.asList("Curiosity", "Opportunity", "Spirit");
        newAreaState.getHovers().stream().map(Hover::getName).forEach(n -> assertTrue(names.contains(n)));
        List<Coordinate> hoverLocations = hovers.stream().map(Hover::getCurrentLocation).collect(Collectors.toList());
        newAreaState.getHovers().forEach(h -> assertTrue(hoverLocations.contains(h.getCurrentLocation())));
    }

    @Test(expected = HoverCollisionException.class)
    public void testDeployMultiplesHoversWithCollisionWhenDeploying() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);
        Hover opportunity = new Hover("Opportunity", new Coordinate(0, 0), Direction.NORTH);

        // When
        areaService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testDeployHoverWithAreaBoundaryTrespassingWhenDeploying() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(5, 5), Direction.NORTH);

        // When
        areaService.deployHover(area, curiosity);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testDeployMultiplesHoversWithCollisionWhenWhenMoving() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);
        Hover opportunity = new Hover("Opportunity", new Coordinate(1, 1), Direction.WEST);
        curiosity.addInstruction(Instruction.MOVE);
        opportunity.addInstruction(Instruction.MOVE);

        // When
        Area newAreaState = areaService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
        areaService.executeHoversInstructions(newAreaState);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testDeployHoverWithAreaBoundaryTrespassingWhenMoving() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(3, 3), Direction.NORTH);
        curiosity.addInstruction(Instruction.MOVE);

        // When
        Area newAreaState = areaService.deployHover(area, curiosity);
        areaService.executeHoversInstructions(newAreaState);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeployNullHoverShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(3, 3));

        // When
        areaService.deployHover(area, null);

        // Then
        fail("Should throw exception");
    }

    @Test
    public void testDeployAndMoveMultiplesHoversAreaAndHoversShouldBeInTheRightState() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover curiosity = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);
        Hover opportunity = new Hover("Opportunity", new Coordinate(1, 1), Direction.EAST);
        curiosity.addInstruction(Instruction.MOVE); // 0, 1
        curiosity.addInstruction(Instruction.MOVE); // 0, 2
        curiosity.addInstruction(Instruction.RIGHT); // 0, 2 East
        curiosity.addInstruction(Instruction.MOVE); // 1, 2 East
        curiosity.addInstruction(Instruction.LEFT); //1, 2 North
        opportunity.addInstruction(Instruction.MOVE); // 2, 1
        opportunity.addInstruction(Instruction.MOVE); // 3, 1

        // When
        Area newAreaState = areaService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
        areaService.executeHoversInstructions(newAreaState);

        // Then
        assertTrue(newAreaState.getHovers().contains(curiosity));
        assertTrue(newAreaState.getHovers().contains(opportunity));
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteHoverInstructionsWithoutHoversInArea() {
        // Given
        Area area = new Area(new Coordinate(5, 5));

        // When
        areaService.executeHoversInstructions(area);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteHoverInstructionWithNullArea() {
        // When
        areaService.executeHoversInstructions(null);

        // Then
        fail("Should throw exception");
    }
}