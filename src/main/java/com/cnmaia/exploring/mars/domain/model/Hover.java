package com.cnmaia.exploring.mars.domain.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cmaia on 8/26/17
 */
public class Hover {
    private final String name;
    private final Coordinate initialLocation;
    private final Direction initialDirection;
    private Coordinate currentLocation;
    private Direction currentFacingDirection;
    private final List<Instruction> instructionHistory = new LinkedList<>();

    public Hover(String name, Coordinate initialLocation, Direction currentFacingDirection) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Hover name cannot be null or empty");
        }

        if (initialLocation == null) {
            throw new IllegalArgumentException("Initial hover location cannot be null");
        }

        if (currentFacingDirection == null) {
            throw new IllegalArgumentException("Initial hover currentFacingDirection direction cannot be null");
        }

        this.name = name;
        this.currentLocation = initialLocation;
        this.currentFacingDirection = currentFacingDirection;
        this.initialLocation = initialLocation;
        this.initialDirection = currentFacingDirection;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public Direction getCurrentFacingDirection() {
        return currentFacingDirection;
    }

    public Coordinate getInitialLocation() {
        return initialLocation;
    }

    public Direction getInitialDirection() {
        return initialDirection;
    }

    public List<Instruction> getInstructionHistory() {
        return instructionHistory;
    }

    // TODO - This should not leak
    public void setCurrentLocation(Coordinate currentLocation) {
        this.currentLocation = currentLocation;
    }


    // TODO - This should not leak
    public void setCurrentFacingDirection(Direction currentFacingDirection) {
        this.currentFacingDirection = currentFacingDirection;
    }

    public void addInstruction(Instruction instruction) {
        if (instruction == null) {
            throw new IllegalArgumentException("Instruction cannot be null to be on history");
        }

        this.instructionHistory.add(instruction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hover hover = (Hover) o;

        return name != null ? name.equals(hover.name) : hover.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
