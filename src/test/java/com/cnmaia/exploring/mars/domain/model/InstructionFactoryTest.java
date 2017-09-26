package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by cmaia on 9/25/17.
 */
public class InstructionFactoryTest {
    @Test
    public void testCreateInstructionMoveShouldReturnCorrectInstance() {
        // When
        Instruction instruction = InstructionFactory.create('M');

        // Then
        assertNotNull(instruction);
        assertTrue(instruction instanceof MoveInstruction);
    }

    @Test
    public void testCreateInstructionLeftRotationShouldReturnCorrectInstance() {
        // When
        Instruction instruction = InstructionFactory.create('L');

        // Then
        assertNotNull(instruction);
        assertTrue(instruction instanceof LeftRotateInstruction);
    }

    @Test
    public void testCreateInstructionRightRotationShouldReturnCorrectInstance() {
        // When
        Instruction instruction = InstructionFactory.create('R');

        // Then
        assertNotNull(instruction);
        assertTrue(instruction instanceof RightRotateInstruction);
    }
}