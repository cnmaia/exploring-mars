package com.cnmaia.exploring.mars.domain.model;

/**
 * Created by cmaia on 9/25/17
 */
public class InstructionFactory {

    public static Instruction create(char instruction) {
        if (instruction == 'M') return new MoveInstruction();
        else if (instruction == 'L') return new LeftRotateInstruction();
        return new RightRotateInstruction();
    }
}
