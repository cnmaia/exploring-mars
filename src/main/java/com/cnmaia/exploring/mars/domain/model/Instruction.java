package com.cnmaia.exploring.mars.domain.model;

/**
 * Created by cmaia on 8/26/17
 */
// TODO - This could be a Movement instruction or a Direction instruction, but inheritance could not be a good idea, prefer composition
public enum Instruction {
    MOVE('M'),
    LEFT('L'),
    RIGHT('R');

    private final char value;

    Instruction(char value) {
        this.value = value;
    }

    private Instruction fromValue(char value) {
        for (Instruction i : Instruction.values()) {
            if (i.value == value) return i;
        }

        return null;
    }
}
