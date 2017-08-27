package com.cnmaia.exploring.mars.domain.model;

/**
 * Created by cmaia on 8/26/17
 */
public class Area {
    private Coordinate upperRightCoordinate;
    private Coordinate lowerLeftCoordinate = new Coordinate(0,0);
    private Coordinate upperLeftCoordinate;
    private Coordinate lowerRightCoordinate;

    public Area(Coordinate upperRightCoordinate) {
        if (upperRightCoordinate == null)
            throw new IllegalArgumentException("Upper right boundary cannot be null");

        this.upperRightCoordinate = upperRightCoordinate;
        this.calculateCoordinates();
    }

    private void calculateCoordinates() {
        this.upperLeftCoordinate = new Coordinate(0, upperRightCoordinate.getY());
        this.lowerRightCoordinate = new Coordinate(upperRightCoordinate.getX(), 0);
    }

    public Coordinate getUpperRightCoordinate() {
        return upperRightCoordinate;
    }

    public Coordinate getUpperLeftCoordinate() {
        return upperLeftCoordinate;
    }

    public Coordinate getLowerRightCoordinate() {
        return lowerRightCoordinate;
    }

    public Coordinate getLowerLeftCoordinate() {
        return lowerLeftCoordinate;
    }
}
