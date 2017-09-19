package com.cnmaia.exploring.mars.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.model.LeftRotateInstruction;
import com.cnmaia.exploring.mars.domain.model.MoveInstruction;
import com.cnmaia.exploring.mars.domain.model.RightRotateInstruction;
import com.cnmaia.exploring.mars.domain.service.HoverDomainService;

/**
 * Created by cmaia on 9/11/17.
 */
public class HoverDomainServiceImplTest {

    private static HoverDomainService hoverDomainService;

    @BeforeClass
    public static void setUp() {
        hoverDomainService = new HoverDomainServiceImpl();
    }

    @Test
    public void testExecuteNextInstructionWithEmptyInstructionShouldMaintainInitialOrder() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        Hover updatedHover = hoverDomainService.executeNextInstruction(hover);

        // Then
        assertEquals(new Coordinate(1, 1), updatedHover.getCurrentLocation());
        assertEquals(Direction.NORTH, updatedHover.getCurrentFacingDirection());
    }

    @Test
    public void testExecuteNextInstructionWhenAllInstructionWereExecutedShouldMaintainLastState() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new MoveInstruction());
        Hover updatedHover = hoverDomainService.executeNextInstruction(hover);

        for (int i = 0; i < 2; i++) {
            updatedHover = hoverDomainService.executeNextInstruction(updatedHover);
        }

        // Then
        assertEquals(new Coordinate(1, 3), updatedHover.getCurrentLocation());
    }

    @Test
    public void testExecuteAllInstructions() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        hover.addInstruction(new MoveInstruction());
        Hover updatedHover = hoverDomainService.executeAllLeftInstructions(hover);

        // Then
        assertEquals(new Coordinate(0, 3), updatedHover.getCurrentLocation());
        assertEquals(Direction.WEST, updatedHover.getCurrentFacingDirection());
        assertEquals(Direction.NORTH, updatedHover.getInitialDirection());
        assertEquals(new Coordinate(1, 1), updatedHover.getInitialLocation());
    }

    @Test
    public void testExecuteAllInstructionOneByOne() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        hover.addInstruction(new MoveInstruction());

        Hover updatedHover = hoverDomainService.executeNextInstruction(hover);

        for (int i = 0; i < 3; i++) {
            updatedHover = hoverDomainService.executeNextInstruction(updatedHover);
        }

        // Then
        assertEquals(new Coordinate(0, 3), updatedHover.getCurrentLocation());
        assertEquals(new Coordinate(1, 1), updatedHover.getInitialLocation());
    }

    @Test
    public void testExecuteHalfInstructions() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        hover.addInstruction(new MoveInstruction());
        Hover updatedHover = hoverDomainService.executeNextInstruction(hover);
        updatedHover = hoverDomainService.executeNextInstruction(updatedHover);

        // Then
        assertEquals(new Coordinate(1, 3), updatedHover.getCurrentLocation());
        assertEquals(Direction.NORTH, updatedHover.getCurrentFacingDirection());
        assertTrue(updatedHover.getInstructionHistory().stream().filter(i -> !i.isExecuted()).count() == 2);
    }

    @Test
    public void testHoverDoesNotChangedInitialLocationAndDirectionAfterInstructionExecution() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        Hover updatedHover = hoverDomainService.executeAllLeftInstructions(hover);

        // Then
        assertEquals(Direction.NORTH, updatedHover.getInitialDirection());
        assertEquals(new Coordinate(1, 1), updatedHover.getInitialLocation());
    }

    @Test
    public void testHoverIsFacingInitialDirectionIfMoveInstructionIsExecuted() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        Hover updatedHover = hoverDomainService.executeAllLeftInstructions(hover);

        // Then
        assertEquals(updatedHover.getCurrentFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testHoverIsFacingNorthDirectionAfterAFullRotationInTheLeftDirection() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new LeftRotateInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        Hover updatedHover = hoverDomainService.executeAllLeftInstructions(hover);

        // Then
        assertEquals(updatedHover.getCurrentFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testHoverIsFacingNorthDirectionAfterAFullRotationInTheRightDirection() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.WEST);

        // When
        hover.addInstruction(new RightRotateInstruction());
        hover.addInstruction(new RightRotateInstruction());
        hover.addInstruction(new RightRotateInstruction());
        hover.addInstruction(new RightRotateInstruction());
        Hover updatedHover = hoverDomainService.executeAllLeftInstructions(hover);

        // Then
        assertEquals(updatedHover.getCurrentFacingDirection(), Direction.WEST);
    }
}