package com.cnmaia.exploring.mars.domain.model;

/**
 * Created by cmaia on 9/1/17
 */
public abstract class Instruction {
    private boolean executed = false;
    private final Movement movement;

    public Instruction(Movement movement) {
        this.movement = movement;
    }

    public boolean isExecuted() {
        return executed;
    }

    public Instruction setExecutedTrue() {
        this.executed = true;
        return this;
    }

    public Movement getMovement() {
        return movement;
    }
}
