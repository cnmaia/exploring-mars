package com.cnmaia.exploring.mars.domain.model;

/**
 * Created by cmaia on 8/26/17
 */
public class Area {
    private Coordinate upperRightBoundary;
    private Coordinate lowerLeftBoundary = new Coordinate(0,0);
    private Coordinate upperLeftBoundary;
    private Coordinate lowerRightBoundary;

    public Area(Coordinate upperRightBoundary) {
        if (upperRightBoundary == null)
            throw new IllegalArgumentException("Upper right boundary cannot be null");

        this.upperRightBoundary = upperRightBoundary;
        this.upperLeftBoundary = new Coordinate(0, upperRightBoundary.getY());
        this.lowerRightBoundary = new Coordinate(upperRightBoundary.getX(), 0);
    }

    public Coordinate getUpperRightBoundary() {
        return upperRightBoundary;
    }

    public Coordinate getUpperLeftBoundary() {
        return upperLeftBoundary;
    }

    public Coordinate getLowerRightBoundary() {
        return lowerRightBoundary;
    }

    public Coordinate getLowerLeftBoundary() {
        return lowerLeftBoundary;
    }
}
