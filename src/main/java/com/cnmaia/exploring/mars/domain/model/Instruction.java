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
    private boolean executed = false;

    Instruction(char value) {
        this.value = value;
    }

    private Instruction fromValue(char value) {
        for (Instruction i : Instruction.values()) {
            if (i.value == value) return i;
        }

        return null;
    }

    public boolean isExecuted() {
        return executed;
    }

    // TODO Don't know yet if this is a good approach
    public void setExecutedTrue() {
        this.executed = executed;
    }
}
