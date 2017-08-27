package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by cmaia on 8/27/17.
 */
public class HoverTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateHoverWithNullNameShouldThrowException() {
        // Given
        new Hover(null, new Coordinate(1, 1), Direction.EAST);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateHoverWithEmptyNameShouldThrowException() {
        // Given
        new Hover("", new Coordinate(1, 1), Direction.EAST);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateHoverWithWhitespacesInNameShouldThrowException() {
        // Given
        new Hover("   ", new Coordinate(1, 1), Direction.EAST);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateHoverWithNullLocationShouldThrowException() {
        // Given
        new Hover("Curiosity", null, Direction.NORTH);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateHoverWithNullDirectionShouldThrowException() {
        // Given
        new Hover("Curiosity", new Coordinate(1, 1), null);

        // Then
        fail("Should throw exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertNullInstructionInHoverInstructionHistory() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(null);

        // Then
        fail("Should throw exception");
    }

    @Test
    public void testInsertInstructionInHoverInstructionHistory() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

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
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

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
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testHoverIsFacingTheRightDirectionAfterInsertInstructions() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(Instruction.RIGHT); // facing right
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.EAST);
    }

    @Test
    public void testHoverIsFacingInitialDirectionIfMoveInstructionIsInserted() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testHoverIsFacingNorthDirectionAfterAFullRotationInTheLeftDirection() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

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
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.WEST);

        // When
        hover.addInstruction(Instruction.RIGHT);
        hover.addInstruction(Instruction.RIGHT);
        hover.addInstruction(Instruction.RIGHT);
        hover.addInstruction(Instruction.RIGHT);

        // Then
        assertEquals(hover.getFacingDirection(), Direction.WEST);
    }

    @Test
    public void testMoveHoverInNorthDirectionShouldCalculateRightLocation() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getCurrentLocation(), new Coordinate(1, 2));
    }

    @Test
    public void testMoveHoverInEastDirectionShouldCalculateRightLocation() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);

        // When
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getCurrentLocation(), new Coordinate(2, 1));
    }

    @Test
    public void testMoveHoverInSouthDirectionShouldCalculateRightLocation() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.SOUTH);

        // When
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getCurrentLocation(), new Coordinate(1, 0));
    }

    @Test
    public void testMoveHoverInWestDirectionShouldCalculateRightLocation() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.WEST);

        // When
        hover.addInstruction(Instruction.MOVE);

        // Then
        assertEquals(hover.getCurrentLocation(), new Coordinate(0, 1));
    }

    @Test
    public void testMoveHoverInVariousDirectionsShouldCalculateRightLocation() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.EAST);

        // When
        hover.addInstruction(Instruction.MOVE); // 2, 1
        hover.addInstruction(Instruction.LEFT); // North
        hover.addInstruction(Instruction.MOVE); // 2, 2
        hover.addInstruction(Instruction.MOVE); // 2, 3
        hover.addInstruction(Instruction.LEFT); // West
        hover.addInstruction(Instruction.MOVE); // 1, 3

        // Then
        assertEquals(hover.getCurrentLocation(), new Coordinate(1, 3));
    }
}