package com.cnmaia.exploring.mars.domain.service.impl;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cnmaia.exploring.mars.domain.exception.HoverCollisionException;
import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.model.LeftRotateInstruction;
import com.cnmaia.exploring.mars.domain.model.MoveInstruction;
import com.cnmaia.exploring.mars.domain.model.RightRotateInstruction;
import com.cnmaia.exploring.mars.domain.service.AreaDomainService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cmaia on 8/27/17.
 */
public class AreaDomainServiceImplTest {

    private static AreaDomainService areaDomainService;

    @BeforeClass
    public static void setup() {
        areaDomainService = new AreaDomainServiceImpl();
    }

    @Test
    public void testDeploySingleHover() {
        // Given
        Coordinate hoverCoordinates = new Coordinate(0, 0);
        Area area = new Area(new Coordinate(1, 1));
        Hover hover = new Hover("Curiosity", hoverCoordinates, Direction.NORTH);

        // When
        Area newAreaState = areaDomainService.deployHover(area, hover);

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
        Area newAreaState = areaDomainService.deployHovers(area, new HashSet<>(hovers));

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
        areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testDeployHoverWithAreaBoundaryTrespassingWhenDeploying() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(5, 5), Direction.NORTH);

        // When
        areaDomainService.deployHover(area, curiosity);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testDeployMultiplesHoversWithCollisionWhenWhenMoving() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);
        Hover opportunity = new Hover("Opportunity", new Coordinate(1, 1), Direction.WEST);
        curiosity.addInstruction(new MoveInstruction());
        opportunity.addInstruction(new MoveInstruction());

        // When
        Area newAreaState = areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
        areaDomainService.executeHoversInstructions(newAreaState);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testDeployHoverWithAreaBoundaryTrespassingWhenMoving() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(3, 3), Direction.NORTH);
        curiosity.addInstruction(new MoveInstruction());

        // When
        Area newAreaState = areaDomainService.deployHover(area, curiosity);
        areaDomainService.executeHoversInstructions(newAreaState);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeployNullHoverShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(3, 3));

        // When
        areaDomainService.deployHover(area, null);

        // Then
        fail("Should throw exception");
    }

    @Test
    public void testDeployAndMoveMultiplesHoversAreaAndHoversShouldBeInTheRightState() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover curiosity = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);
        Hover opportunity = new Hover("Opportunity", new Coordinate(1, 1), Direction.EAST);
        curiosity.addInstruction(new MoveInstruction()); // 0, 1
        curiosity.addInstruction(new MoveInstruction()); // 0, 2
        curiosity.addInstruction(new RightRotateInstruction()); // 0, 2 East
        curiosity.addInstruction(new MoveInstruction()); // 1, 2 East
        curiosity.addInstruction(new LeftRotateInstruction()); //1, 2 North
        opportunity.addInstruction(new MoveInstruction()); // 2, 1
        opportunity.addInstruction(new MoveInstruction()); // 3, 1

        // When
        Area newAreaState = areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
        areaDomainService.executeHoversInstructions(newAreaState);

        // Then
        assertTrue(newAreaState.getHovers().contains(curiosity));
        assertTrue(newAreaState.getHovers().contains(opportunity));
    }

    @Test(expected = IllegalStateException.class)
    public void testExecuteHoverInstructionsWithoutHoversInArea() {
        // Given
        Area area = new Area(new Coordinate(5, 5));

        // When
        areaDomainService.executeHoversInstructions(area);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteHoverInstructionWithNullArea() {
        // When
        areaDomainService.executeHoversInstructions(null);

        // Then
        fail("Should throw exception");
    }

    @Test
    public void testMoveHoverInNorthDirectionShouldCalculateRightLocation() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        areaDomainService.deployHover(area, hover);
        areaDomainService.executeHoversInstructions(area);

        // Then
        assertEquals(new Coordinate(1, 2), area.getHovers().stream().findFirst().get().getCurrentLocation());
    }

    @Test
    public void testMoveHoverInEastDirectionShouldCalculateRightLocation() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);

        // When
        hover.addInstruction(new MoveInstruction());
        areaDomainService.deployHover(area, hover);
        areaDomainService.executeHoversInstructions(area);

        // Then
        assertEquals(new Coordinate(2, 1), area.getHovers().stream().findFirst().get().getCurrentLocation());
    }

    @Test
    public void testMoveHoverInSouthDirectionShouldCalculateRightLocation() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.SOUTH);

        // When
        hover.addInstruction(new MoveInstruction());
        areaDomainService.deployHover(area, hover);
        areaDomainService.executeHoversInstructions(area);

        // Then
        assertEquals(new Coordinate(1, 0), area.getHovers().stream().findFirst().get().getCurrentLocation());
    }

    @Test
    public void testMoveHoverInWestDirectionShouldCalculateRightLocation() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.WEST);

        // When
        hover.addInstruction(new MoveInstruction());
        areaDomainService.deployHover(area, hover);
        areaDomainService.executeHoversInstructions(area);

        // Then
        assertEquals(new Coordinate(0, 1), area.getHovers().stream().findFirst().get().getCurrentLocation());
    }

    @Test
    public void testMoveHoverInVariousDirectionsShouldCalculateRightLocation() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);

        // When
        hover.addInstruction(new MoveInstruction()); // 2, 1
        hover.addInstruction(new LeftRotateInstruction()); // North
        hover.addInstruction(new MoveInstruction()); // 2, 2
        hover.addInstruction(new MoveInstruction()); // 2, 3
        hover.addInstruction(new LeftRotateInstruction()); // West
        hover.addInstruction(new MoveInstruction()); // 1, 3
        areaDomainService.deployHover(area, hover);
        areaDomainService.executeHoversInstructions(area);

        // Then
        assertEquals(new Coordinate(1, 3), area.getHovers().stream().findFirst().get().getCurrentLocation());
    }

    @Test
    public void testMoveMultiplesHovers() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover curiosity = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);
        Hover opportunity = new Hover("Opportunity", new Coordinate(2, 2), Direction.NORTH);

        // When
        curiosity.addInstruction(new MoveInstruction()); // 2, 1
        curiosity.addInstruction(new MoveInstruction()); // 3, 1
        curiosity.addInstruction(new LeftRotateInstruction()); // North
        curiosity.addInstruction(new MoveInstruction()); // 3, 2
        curiosity.addInstruction(new MoveInstruction()); // 3, 3
        opportunity.addInstruction(new MoveInstruction()); // 2, 3
        opportunity.addInstruction(new RightRotateInstruction()); // EAST

        areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
        areaDomainService.executeHoversInstructions(area);

        // Then
        assertEquals(new Coordinate(3, 3), area.getHovers().stream().filter(h -> h.getName().equals("Curiosity")).findFirst().get().getCurrentLocation());
        assertEquals(Direction.NORTH, area.getHovers().stream().filter(h -> h.getName().equals("Curiosity")).findFirst().get().getFacingDirection());
        assertEquals(new Coordinate(2, 3), area.getHovers().stream().filter(h -> h.getName().equals("Opportunity")).findFirst().get().getCurrentLocation());
        assertEquals(Direction.EAST, area.getHovers().stream().filter(h -> h.getName().equals("Opportunity")).findFirst().get().getFacingDirection());
    }

    @Test(expected = HoverCollisionException.class)
    public void testMoveHoverCollideWithWallShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover curiosity = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);
        Hover opportunity = new Hover("Opportunity", new Coordinate(2, 2), Direction.NORTH);

        // When
        curiosity.addInstruction(new MoveInstruction()); // 2, 1
        curiosity.addInstruction(new MoveInstruction()); // 3, 1
        curiosity.addInstruction(new MoveInstruction()); // 4, 1
        curiosity.addInstruction(new MoveInstruction()); // 5, 1
        curiosity.addInstruction(new MoveInstruction()); // 6, 1 - Collide
        opportunity.addInstruction(new MoveInstruction()); // 3, 2
        opportunity.addInstruction(new MoveInstruction()); // 4, 2
        opportunity.addInstruction(new MoveInstruction()); // 5, 2
        opportunity.addInstruction(new MoveInstruction()); // 6, 2 - Collide

        areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
        areaDomainService.executeHoversInstructions(area);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testMoveMultiplesHoverCollidingInBetweenShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover curiosity = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);
        Hover opportunity = new Hover("Opportunity", new Coordinate(2, 2), Direction.NORTH);

        // When
        curiosity.addInstruction(new MoveInstruction()); // 2, 1
        curiosity.addInstruction(new LeftRotateInstruction()); // North
        curiosity.addInstruction(new MoveInstruction()); // 2, 2
        areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
        areaDomainService.executeHoversInstructions(area);

        // Then
        fail("Should throw exception");
    }
}