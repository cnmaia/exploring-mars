package com.cnmaia.exploring.mars.domain.model;

/**
 * Created by cmaia on 9/1/17
 */
public abstract class RotateInstruction extends Instruction {
    public RotateInstruction(Movement instruction) {
        super(instruction);
    }
}
