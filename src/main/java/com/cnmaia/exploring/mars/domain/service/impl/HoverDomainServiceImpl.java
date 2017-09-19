package com.cnmaia.exploring.mars.domain.service.impl;

import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.model.Instruction;
import com.cnmaia.exploring.mars.domain.model.Movement;
import com.cnmaia.exploring.mars.domain.service.HoverDomainService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmaia on 9/6/17
 */
public class HoverDomainServiceImpl implements HoverDomainService {
    @Override
    public Hover executeNextInstruction(final Hover hover) {
        if (hover == null) {
            throw new IllegalArgumentException("Hover cannot be null to perform instructions");
        }

        hover.getInstructionHistory()
                .stream()
                .filter(i -> !i.isExecuted())
                .findFirst()
                .ifPresent(i ->  {
                    i.setExecutedTrue();
                    if (i.getMovement() == Movement.MOVE) {
                        hover.setCurrentLocation(this.calculateNewLocation(hover));
                    } else {
                        hover.setCurrentFacingDirection(this.calculateNewFacingDirection(hover, i));
                    }
                });

        return hover;
    }

    @Override
    public Hover executeAllLeftInstructions(final Hover hover) {
        if (hover == null) {
            throw new IllegalArgumentException("Hover cannot be null to perform instructions");
        }

        hover.getInstructionHistory()
                .stream()
                .filter(i -> !i.isExecuted())
                .map(Instruction::setExecutedTrue)
                .forEach(i -> {
                    if (i.getMovement() == Movement.MOVE) {
                        hover.setCurrentLocation(this.calculateNewLocation(hover));
                    } else {
                        hover.setCurrentFacingDirection(this.calculateNewFacingDirection(hover, i));
                    }
                });

        return hover;
    }

    private Coordinate calculateNewLocation(Hover hover) {
        Map<Direction, Coordinate> possibleLocations = new HashMap<>();

        possibleLocations.put(Direction.NORTH, new Coordinate(hover.getCurrentLocation().getX(), hover.getCurrentLocation().getY() + 1));
        possibleLocations.put(Direction.EAST, new Coordinate(hover.getCurrentLocation().getX() + 1, hover.getCurrentLocation().getY()));
        possibleLocations.put(Direction.SOUTH, new Coordinate(hover.getCurrentLocation().getX(), hover.getCurrentLocation().getY() - 1));
        possibleLocations.put(Direction.WEST, new Coordinate(hover.getCurrentLocation().getX() - 1, hover.getCurrentLocation().getY()));

        return possibleLocations.get(hover.getCurrentFacingDirection());
    }

    // TODO Refactor and find a way to optimize this
    private Direction calculateNewFacingDirection(Hover hover, Instruction instruction) {
        if (instruction == null) {
            throw new IllegalArgumentException("Instruction cannot be null when calculating the new facing direction");
        }

        if (instruction.getMovement() == Movement.MOVE) {
            return hover.getCurrentFacingDirection();
        }

        Direction[] possibleDirections = Direction.values();
        int index = 0;

        // Get the index in array where the last facing direction was
        for (int i = 0; i < possibleDirections.length; i++) {
            if (possibleDirections[i] == hover.getCurrentFacingDirection()) {
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
        }

        // LEFT MOVEMENT
        if (index == 0) {
            return possibleDirections[possibleDirections.length - 1];
        }

        return possibleDirections[index - 1];
    }
}
