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
    private Direction facingDirection;
    // TODO - Check, maybe this should be a stack
    private final List<Instruction> instructionHistory = new LinkedList<>();

    public Hover(String name, Coordinate initialLocation, Direction facingDirection) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Hover name cannot be null or empty");
        }

        if (initialLocation == null) {
            throw new IllegalArgumentException("Initial hover location cannot be null");
        }

        if (facingDirection == null) {
            throw new IllegalArgumentException("Initial hover facingDirection direction cannot be null");
        }

        this.name = name;
        this.currentLocation = initialLocation;
        this.facingDirection = facingDirection;
        this.initialLocation = initialLocation;
        this.initialDirection = facingDirection;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public Coordinate getInitialLocation() {
        return initialLocation;
    }

    public Direction getInitialDirection() {
        return initialDirection;
    }

    public void addInstruction(Instruction instruction) {
        if (instruction == null) {
            throw new IllegalArgumentException("Instruction cannot be null to be on history");
        }

        if (instruction != Instruction.MOVE) {
            this.facingDirection = calculateNewFacingDirection(instruction);
        } else {
            this.currentLocation = calculateNewLocation();
        }

        this.instructionHistory.add(instruction);
    }

    public void performInstructions() {

    }

    public List<Instruction> getInstructionHistory() {
        return instructionHistory;
    }

    private Coordinate calculateNewLocation() {
        if (facingDirection == Direction.NORTH) {
            return new Coordinate(currentLocation.getX(), currentLocation.getY() + 1);
        }

        if (facingDirection == Direction.EAST) {
            return new Coordinate(currentLocation.getX() + 1, currentLocation.getY());
        }

        if (facingDirection == Direction.SOUTH) {
            return new Coordinate(currentLocation.getX(), currentLocation.getY() - 1);
        }

        return new Coordinate(currentLocation.getX() - 1, currentLocation.getY());
    }

    // TODO Refactor and find a way to optimize this
    private Direction calculateNewFacingDirection(Instruction instruction) {
        if (instruction == null) {
            throw new IllegalArgumentException("Instruction cannot be null when calculating the new facing direction");
        }

        Direction[] possibleDirections = Direction.values();
        int index = 0;

        // Get the index in array where the last facing direction was
        for (int i = 0; i < possibleDirections.length; i++) {
            if (possibleDirections[i] == this.facingDirection) {
                index = i;
                break;
            }
        }

        // Walk in array rotating if the index is the last one (or the first) to get the new facing direction
        if (instruction == Instruction.RIGHT) {
            if (index == possibleDirections.length - 1) {
                return possibleDirections[0];
            }

            return possibleDirections[index + 1];
        } else if (instruction == Instruction.LEFT) {
            if (index == 0) {
                return possibleDirections[possibleDirections.length - 1];
            }

            return possibleDirections[index - 1];
        }

        return null; // TODO Do not return null here
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hover hover = (Hover) o;

        if (name != null ? !name.equals(hover.name) : hover.name != null) return false;
        if (currentLocation != null ? !currentLocation.equals(hover.currentLocation) : hover.currentLocation != null)
            return false;
        return facingDirection == hover.facingDirection;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (currentLocation != null ? currentLocation.hashCode() : 0);
        result = 31 * result + (facingDirection != null ? facingDirection.hashCode() : 0);
        return result;
    }
}
