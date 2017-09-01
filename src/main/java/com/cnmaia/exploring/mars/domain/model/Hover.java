package com.cnmaia.exploring.mars.domain.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by cmaia on 8/26/17
 */
public class Hover {
    private final String name;
    private final Coordinate initialLocation;
    private final Direction initialDirection;
    private Coordinate currentLocation;
    private Direction facingDirection;
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

    public List<Instruction> getInstructionHistory() {
        return instructionHistory;
    }

    public void addInstruction(Instruction instruction) {
        if (instruction == null) {
            throw new IllegalArgumentException("Instruction cannot be null to be on history");
        }

        this.instructionHistory.add(instruction);
    }

    public Hover executeNextInstruction() {
        for (int i = 0; i < this.instructionHistory.size(); i++) {

            Instruction instruction = this.instructionHistory.get(i);

            if (!instruction.isExecuted()) {

                if (instruction.getMovement() == Movement.MOVE) {
                    this.currentLocation = calculateNewLocation();
                } else {
                    this.facingDirection = calculateNewFacingDirection(instruction);
                }

                this.instructionHistory.set(i, instruction.setExecutedTrue());

                break;
            }
        }

        return this;
    }

    public Hover executeAllLeftInstructions() {
        long instructionNotExecutedYet = this.instructionHistory.stream().filter(i -> !i.isExecuted()).count();

        for (long i = 0; i < instructionNotExecutedYet; i++) {
            executeNextInstruction();
        }

        return this;
    }

    private Coordinate calculateNewLocation() {
        Map<Direction, Coordinate> possibleLocations = new HashMap<>();

        possibleLocations.put(Direction.NORTH, new Coordinate(currentLocation.getX(), currentLocation.getY() + 1));
        possibleLocations.put(Direction.EAST, new Coordinate(currentLocation.getX() + 1, currentLocation.getY()));
        possibleLocations.put(Direction.SOUTH, new Coordinate(currentLocation.getX(), currentLocation.getY() - 1));
        possibleLocations.put(Direction.WEST, new Coordinate(currentLocation.getX() - 1, currentLocation.getY()));

        return possibleLocations.get(facingDirection);
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
        if (instruction.getMovement() == Movement.RIGHT) {
            if (index == possibleDirections.length - 1) {
                return possibleDirections[0];
            }

            return possibleDirections[index + 1];
        } else if (instruction.getMovement() == Movement.LEFT) {
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

        return name != null ? name.equals(hover.name) : hover.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
