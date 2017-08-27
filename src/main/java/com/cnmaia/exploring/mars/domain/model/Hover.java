package com.cnmaia.exploring.mars.domain.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cmaia on 8/26/17
 */
public class Hover {
    private Coordinate initialLocation;
    private Direction facingDirection;
    // TODO - Check this, maybe this should be a stack
    private List<Instruction> instructionHistory = new LinkedList<>();

    public Hover(Coordinate initialLocation, Direction facingDirection) {
        if (initialLocation == null) {
            throw new IllegalArgumentException("Initial hover location cannot be null");
        }

        if (facingDirection == null) {
            throw new IllegalArgumentException("Initial hover facingDirection direction cannot be null");
        }

        this.initialLocation = initialLocation;
        this.facingDirection = facingDirection;
    }

    public Coordinate getInitialLocation() {
        return initialLocation;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void addInstructionHistory(Instruction instruction) {
        if (instruction == null) {
            throw new IllegalArgumentException("Instruction cannot be null to be on history");
        }

        this.instructionHistory.add(instruction);
    }

    public List<Instruction> getInstructionHistory() {
        return instructionHistory;
    }
}
