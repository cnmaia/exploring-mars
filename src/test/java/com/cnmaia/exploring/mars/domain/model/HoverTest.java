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
    public void testInsertNullInstructionInHoverInstructionHistoryShouldThrowException() {
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
        hover.addInstruction(new RightRotateInstruction());

        // Then
        assertNotNull(hover);
        assertTrue(hover.getInstructionHistory().size() == 1);
        assertEquals(hover.getInstructionHistory().get(0).getMovement(), Movement.RIGHT);
    }

    @Test
    public void testInsertVariousInstructionHistoryMaintainInsertOrder() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new RightRotateInstruction());
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        hover.addInstruction(new MoveInstruction());

        // Then
        assertEquals(hover.getInstructionHistory().get(0).getMovement(), Movement.RIGHT);
        assertEquals(hover.getInstructionHistory().get(1).getMovement(), Movement.MOVE);
        assertEquals(hover.getInstructionHistory().get(2).getMovement(), Movement.LEFT);
        assertEquals(hover.getInstructionHistory().get(3).getMovement(), Movement.MOVE);
    }

    @Test
    public void testCreatedHoverIsFacingTheRightDirection() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // Then
        assertEquals(hover.getCurrentFacingDirection(), Direction.NORTH);
    }

    @Test
    public void testInsertHoverInstructionShouldNotBeExecuted() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new LeftRotateInstruction());
        hover.addInstruction(new MoveInstruction());

        // Then
        assertEquals(new Coordinate(1, 1), hover.getCurrentLocation());
        assertEquals(Direction.NORTH, hover.getCurrentFacingDirection());
        assertEquals(Direction.NORTH, hover.getInitialDirection());
    }
}