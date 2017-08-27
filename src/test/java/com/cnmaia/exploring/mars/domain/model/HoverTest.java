package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by cmaia on 8/27/17.
 */
public class HoverTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateHoverWithNullLocationShouldThrowException() {
        // Given
        new Hover(null, Direction.NORTH);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateHoverWithNullDirectionShouldThrowException() {
        // Given
        new Hover(new Coordinate(1, 1), null);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertNullInstructionInHoverInstructionHistory() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(null);

        // Then
        fail("Should throw exception");
    }

    @Test
    public void testInsertInstructionInHoverInstructionHistory() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(Instruction.RIGHT);

        // Then
        assertNotNull(hover);
        assertTrue(hover.getInstructionHistory().size() == 1);
        assertEquals(hover.getInstructionHistory().get(0), Instruction.RIGHT);
    }

    @Test
    public void testInsertVariousInstructionHistoryMaintainInsertOrder() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(Instruction.RIGHT);
        hover.addInstruction(Instruction.MOVE);
        hover.addInstruction(Instruction.LEFT);
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getInstructionHistory().get(0), Instruction.RIGHT);
        assertEquals(hover.getInstructionHistory().get(1), Instruction.MOVE);
        assertEquals(hover.getInstructionHistory().get(2), Instruction.LEFT);
        assertEquals(hover.getInstructionHistory().get(3), Instruction.MOVE);
    }

    @Test
    public void testCreatedHoverIsFacingTheRightDirection() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.NORTH);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testHoverIsFacingTheRightDirectionAfterInsertInstructions() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(Instruction.RIGHT); // facing right
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.EAST);
    }

    @Test
    public void testHoverIsFacingInitialDirectionIfMoveInstructionIsInserted() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testHoverIsFacingNorthDirectionAfterAFullRotationInTheLeftDirection() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(Instruction.LEFT);
        hover.addInstruction(Instruction.LEFT);
        hover.addInstruction(Instruction.LEFT);
        hover.addInstruction(Instruction.LEFT);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testHoverIsFacingNorthDirectionAfterAFullRotationInTheRightDirection() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.WEST);

        // When
        hover.addInstruction(Instruction.RIGHT);
        hover.addInstruction(Instruction.RIGHT);
        hover.addInstruction(Instruction.RIGHT);
        hover.addInstruction(Instruction.RIGHT);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.WEST);
    }
}