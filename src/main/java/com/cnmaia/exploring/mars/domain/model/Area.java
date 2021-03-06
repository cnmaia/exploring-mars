package com.cnmaia.exploring.mars.domain.model;

import com.cnmaia.exploring.mars.domain.exception.HoverCollisionException;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cmaia on 8/26/17
 */
public class Area {
    private final Coordinate upperRightCoordinate;
    private final Coordinate lowerLeftCoordinate = new Coordinate(0,0);
    private Coordinate upperLeftCoordinate;
    private Coordinate lowerRightCoordinate;
    private final Set<Hover> hovers = new LinkedHashSet<>(); // Maintain insertion order for instructions execution

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

    public Set<Hover> getHovers() {
        return new LinkedHashSet<>(this.hovers);
    }

    public void addHover(Hover hover) {
        if (hover == null) {
            throw new IllegalArgumentException("Cannot add a null hover to areas");
        }

        if (this.hovers.contains(hover)) {
            this.hovers.remove(hover);
        }

        this.hovers.add(hover);

        this.checkForCollision();
    }

    public void updateHover(Hover hover) {
        if (hover == null) {
            throw new IllegalArgumentException("Cannot update a null hover in area");
        }

        this.addHover(hover);
    }

    // TODO Refactor to know what hovers collided when they have a name
    private void checkForCollision() {
        Set<Coordinate> currentHoverLocations = hovers.stream().map(Hover::getCurrentLocation).collect(Collectors.toSet());

        if (currentHoverLocations.size() < this.hovers.size()) {
            // This means that has duplicate location for hovers - so hover collision in between

            throw new HoverCollisionException("Hovers collided! Be careful moving or deploy hovers to they not collide, " +
                    "they are in mars!");
        }

        for (Hover hover : hovers) {
            if (hover.getCurrentLocation().getY() > upperRightCoordinate.getY()
                    || hover.getCurrentLocation().getY() < lowerLeftCoordinate.getY()
                    || hover.getCurrentLocation().getX() > upperRightCoordinate.getX()
                    || hover.getCurrentLocation().getX() < lowerLeftCoordinate.getX()) {
                // This means that hover has collided with the area wall

                throw new HoverCollisionException("Hover collided with the area boundaries! Be careful moving or deploy " +
                        "hovers to they not collide, they are in mars!");
            }
        }
    }
}
