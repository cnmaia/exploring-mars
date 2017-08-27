package com.cnmaia.exploring.mars.domain.model;

/**
 * Created by cmaia on 8/26/17
 */
public class Hover {
    private Coordinate initialLocation;
    private Direction facing;

    public Hover(Coordinate initialLocation, Direction facing) {
        this.initialLocation = initialLocation;
        this.facing = facing;
    }

    public Coordinate getInitialLocation() {
        return initialLocation;
    }

    public Direction getFacing() {
        return facing;
    }
}
