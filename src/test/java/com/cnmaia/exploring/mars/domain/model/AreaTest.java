package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Created by cmaia on 8/26/17.
 */
public class AreaTest {

    @Test
    public void testAreaIsARectangle() {
        Area area = new Area(new Coordinate(1, 1));

        assertNotNull(area);
        assertEquals(area.getUpperRightBoundary().getX(), 1);
        assertEquals(area.getUpperRightBoundary().getY(), 1);
        assertEquals(area.getLowerLeftBoundary().getX(), 0);
        assertEquals(area.getLowerLeftBoundary().getY(), 0);
    }

    @Test
    public void testLowerLeftPointsAreZero() {
        Area area = new Area(new Coordinate(1, 1));

        assertNotNull(area);
        assertEquals(area.getLowerLeftBoundary().getX(), 0);
        assertEquals(area.getLowerRightBoundary().getY(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCoordinatesShouldThrowException() {
        new Area(null);
    }
}