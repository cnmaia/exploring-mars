package com.cnmaia.exploring.mars.domain.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cnmaia.exploring.mars.domain.exception.HoverCollisionException;
import com.cnmaia.exploring.mars.domain.exception.ValidationException;
import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.model.MoveInstruction;
import com.cnmaia.exploring.mars.domain.service.HoverDomainService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cmaia on 8/27/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AreaDomainServiceImplTest {

    @Mock
    private HoverDomainService hoverDomainService;

    @InjectMocks
    private AreaDomainServiceImpl areaDomainService;

    @Before
    public void setup() {
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
    public void testDeployMultiplesHoversWithCollisionWhenMoving() {
        // Given
        Area area = new Area(new Coordinate(3, 3));
        Hover curiosity = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);
        Hover opportunity = new Hover("Opportunity", new Coordinate(1, 1), Direction.WEST);
        curiosity.addInstruction(new MoveInstruction().setExecutedTrue());
        opportunity.addInstruction(new MoveInstruction().setExecutedTrue());
        curiosity.setCurrentLocation(new Coordinate(1, 1));

        // When
        Area newAreaState = areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
        areaDomainService.executeHoversInstructions(newAreaState);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = ValidationException.class)
    public void testDeployNullHoverShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(3, 3));

        // When
        areaDomainService.deployHover(area, null);

        // Then
        fail("Should throw exception");
    }

    @Test
    public void testDeployAndMoveMultiplesHoversInAreaAndHoversShouldBeInTheRightState() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover curiosity = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);
        Hover opportunity = new Hover("Opportunity", new Coordinate(1, 1), Direction.EAST);
        when(hoverDomainService.executeAllLeftInstructions(curiosity)).thenReturn(curiosity);
        when(hoverDomainService.executeAllLeftInstructions(opportunity)).thenReturn(opportunity);

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

    @Test(expected = HoverCollisionException.class)
    public void testDeployHoversWithSameLocationShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(5, 5));
        Hover curiosity = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);
        Hover opportunity = new Hover("Opportunity", new Coordinate(1, 1), Direction.NORTH);

        // When
        areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));

        // Then
        fail("Should throw exception");
    }

//    @Test(expected = HoverCollisionException.class)
//    public void testMoveHoverCollideWithWallShouldThrowException() {
//        // Given
//        Area area = new Area(new Coordinate(5, 5));
//        Hover curiosity = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);
//        Hover opportunity = new Hover("Opportunity", new Coordinate(2, 2), Direction.NORTH);
//        curiosity.addInstruction(new MoveInstruction()); // 2, 1
//        curiosity.addInstruction(new MoveInstruction()); // 3, 1
//        curiosity.addInstruction(new MoveInstruction()); // 4, 1
//        curiosity.addInstruction(new MoveInstruction()); // 5, 1
//        curiosity.addInstruction(new MoveInstruction()); // 6, 1 - Collide
//        opportunity.addInstruction(new MoveInstruction()); // 3, 2
//        opportunity.addInstruction(new MoveInstruction()); // 4, 2
//        opportunity.addInstruction(new MoveInstruction()); // 5, 2
//        opportunity.addInstruction(new MoveInstruction()); // 6, 2 - Collide
//
//        when(hoverDomainService.executeAllLeftInstructions(curiosity)).thenReturn(curiosity);
//        when(hoverDomainService.executeAllLeftInstructions(opportunity)).thenReturn(opportunity);
//
//        // When
//        areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
//        areaDomainService.executeHoversInstructions(area);
//
//        // Then
//        fail("Should throw exception");
//    }
//
//    @Test(expected = HoverCollisionException.class)
//    public void testMoveMultiplesHoverCollidingInBetweenShouldThrowException() {
//        // Given
//        Area area = new Area(new Coordinate(5, 5));
//        Hover curiosity = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);
//        Hover opportunity = new Hover("Opportunity", new Coordinate(2, 2), Direction.NORTH);
//
//        // When
//        curiosity.addInstruction(new MoveInstruction()); // 2, 1
//        curiosity.addInstruction(new LeftRotateInstruction()); // North
//        curiosity.addInstruction(new MoveInstruction()); // 2, 2
//        areaDomainService.deployHovers(area, new HashSet<>(Arrays.asList(curiosity, opportunity)));
//        areaDomainService.executeHoversInstructions(area);
//
//        // Then
//        fail("Should throw exception");
//    }
}