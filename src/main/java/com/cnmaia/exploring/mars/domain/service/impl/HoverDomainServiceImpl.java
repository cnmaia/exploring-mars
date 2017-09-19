package com.cnmaia.exploring.mars.domain.service.impl;

import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.model.Instruction;
import com.cnmaia.exploring.mars.domain.model.Movement;
import com.cnmaia.exploring.mars.domain.service.HoverDomainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cmaia on 9/6/17
 */
public class HoverDomainServiceImpl implements HoverDomainService {
    @Override
    public Hover executeNextInstruction(Hover hover) {
        if (hover == null) {
            throw new IllegalArgumentException("Hover cannot be null to perform instructions");
        }

        Hover newHover = new Hover(hover.getName(), hover.getInitialLocation(), hover.getInitialDirection());

        // Add all already executed instructions in new hover
        hover.getInstructionHistory()
                .stream()
                .filter(Instruction::isExecuted)
                .forEach(i -> {
                    newHover.addInstruction(i);
                    if (i.getMovement() == Movement.MOVE) {
                        newHover.setCurrentLocation(this.calculateNewLocation(newHover));
                    } else {
                        newHover.setCurrentFacingDirection(this.calculateNewFacingDirection(newHover, i));
                    }
                });

        // Filter not executed ones
        List<Instruction> notExecutedInstructions = hover.getInstructionHistory()
                .stream()
                .filter(i -> !i.isExecuted())
                .collect(Collectors.toList());

        if (notExecutedInstructions.size() > 0) {
            // Execute next instruction
            notExecutedInstructions.stream().findFirst().ifPresent(i -> {
                i.setExecutedTrue();
                if (i.getMovement() == Movement.MOVE) {
                    newHover.setCurrentLocation(this.calculateNewLocation(newHover));
                } else {
                    newHover.setCurrentFacingDirection(this.calculateNewFacingDirection(newHover, i));
                }
            });

            // Add all left instructions to new hover, including the new executed one
            notExecutedInstructions.forEach(newHover::addInstruction);
        }

        return newHover;
    }

    @Override
    public Hover executeAllLeftInstructions(Hover hover) {
        if (hover == null) {
            throw new IllegalArgumentException("Hover cannot be nul to perform instructions");
        }

        Hover newHover = new Hover(hover.getName(), hover.getInitialLocation(), hover.getInitialDirection());

        hover.getInstructionHistory()
                .stream()
                .filter(Instruction::isExecuted).forEach(i -> {
                    newHover.addInstruction(i);
                    if (i.getMovement() == Movement.MOVE) {
                        newHover.setCurrentLocation(this.calculateNewLocation(newHover));
                    } else {
                        newHover.setCurrentFacingDirection(this.calculateNewFacingDirection(newHover, i));
                    }
                });

        hover.getInstructionHistory()
                .stream()
                .filter(i -> !i.isExecuted())
                .map(Instruction::setExecutedTrue)
                .forEach(i -> {
                    newHover.addInstruction(i);
                    if (i.getMovement() == Movement.MOVE) {
                        newHover.setCurrentLocation(this.calculateNewLocation(newHover));
                    } else {
                        newHover.setCurrentFacingDirection(this.calculateNewFacingDirection(newHover, i));
                    }
                });

        return newHover;
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
