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
        assertEquals(hover.getFacingDirection(), Direction.NORTH);
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
        assertEquals(Direction.NORTH, hover.getFacingDirection());
        assertEquals(Direction.NORTH, hover.getInitialDirection());
    }

    @Test
    public void testExecuteNextInstructionWithEmptyInstructionShouldMaintainInitialOrder() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.executeNextInstruction();

        // Then
        assertEquals(new Coordinate(1, 1), hover.getCurrentLocation());
        assertEquals(Direction.NORTH, hover.getFacingDirection());
    }

    @Test
    public void testExecuteNextInstructionWhenAllInstructionWereExecutedShouldMaintainLastState() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new MoveInstruction());
        hover.executeNextInstruction();
        hover.executeNextInstruction();
        hover.executeNextInstruction();

        // Then
        assertEquals(new Coordinate(1, 3), hover.getCurrentLocation());
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
        hover.executeAllLeftInstructions();

        // Then
        assertEquals(new Coordinate(0, 3), hover.getCurrentLocation());
        assertEquals(Direction.WEST, hover.getFacingDirection());
        assertEquals(Direction.NORTH, hover.getInitialDirection());
        assertEquals(new Coordinate(1, 1), hover.getInitialLocation());
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
        hover.executeNextInstruction();
        hover.executeNextInstruction();
        hover.executeNextInstruction();
        hover.executeNextInstruction();

        // Then
        assertEquals(new Coordinate(0, 3), hover.getCurrentLocation());
        assertEquals(new Coordinate(1, 1), hover.getInitialLocation());
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
        hover.executeNextInstruction();
        hover.executeNextInstruction();

        // Then
        assertEquals(new Coordinate(1, 3), hover.getCurrentLocation());
        assertEquals(Direction.NORTH, hover.getFacingDirection());
        assertTrue(hover.getInstructionHistory().stream().filter(i -> !i.isExecuted()).count() == 2);
    }

    @Test
    public void testHoverDoesNotChangedInitialLocationAndDirectionAfterInstructionExecution() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        hover.addInstruction(new LeftRotateInstruction());
        hover.executeAllLeftInstructions();

        // Then
        assertEquals(Direction.NORTH, hover.getInitialDirection());
        assertEquals(new Coordinate(1, 1), hover.getInitialLocation());
    }

    @Test
    public void testHoverIsFacingInitialDirectionIfMoveInstructionIsExecuted() {
        // Given
        Hover hover = new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH);

        // When
        hover.addInstruction(new MoveInstruction());
        hover.executeAllLeftInstructions();

        // Then
        assertEquals(hover.getFacingDirection(), Direction.NORTH);
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
        hover.executeAllLeftInstructions();

        // Then
        assertEquals(hover.getFacingDirection(), Direction.NORTH);
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
        hover.executeAllLeftInstructions();

        // Then
        assertEquals(hover.getFacingDirection(), Direction.WEST);
    }
}