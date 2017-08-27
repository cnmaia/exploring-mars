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
        hover.addInstructionHistory(null);

        // Then
        fail("Should throw exception");
    }

    @Test
    public void testInsertInstructionInHoverInstructionHistory() {
        // Given
        Hover hover = new Hover(new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstructionHistory(Instruction.RIGHT);

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
        hover.addInstructionHistory(Instruction.RIGHT);
        hover.addInstructionHistory(Instruction.MOVE);
        hover.addInstructionHistory(Instruction.LEFT);
        hover.addInstructionHistory(Instruction.MOVE);

        // Then
        assertEquals(hover.getInstructionHistory().get(0), Instruction.RIGHT);
        assertEquals(hover.getInstructionHistory().get(1), Instruction.MOVE);
        assertEquals(hover.getInstructionHistory().get(2), Instruction.LEFT);
        assertEquals(hover.getInstructionHistory().get(3), Instruction.MOVE);
    }
}