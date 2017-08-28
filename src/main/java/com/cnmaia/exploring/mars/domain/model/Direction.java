package com.cnmaia.exploring.mars.domain.model;

/**
 * Created by cmaia on 8/26/17.
 */
public enum Direction {
    NORTH('N'),
    EAST('E'),
    SOUTH('S'),
    WEST('W');

    private final char value;

    Direction(char value) {
        this.value = value;
    }

    public Direction fromValue(char value) {
        for (Direction d : Direction.values()) {
            if (d.value == value) return d;
        }

        return null;
    }
}
