package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Created by cmaia on 8/26/17.
 */
public class AreaTest {

    @Test
    public void testAreaIsARectangle() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // Then
        assertNotNull(area);
        assertEquals(area.getUpperRightCoordinate().getX(), 1);
        assertEquals(area.getUpperRightCoordinate().getY(), 1);
        assertEquals(area.getLowerLeftCoordinate().getX(), 0);
        assertEquals(area.getLowerLeftCoordinate().getY(), 0);
    }

    @Test
    public void testLowerLeftPointsAreZero() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // Then
        assertNotNull(area);
        assertEquals(area.getLowerLeftCoordinate().getX(), 0);
        assertEquals(area.getLowerRightCoordinate().getY(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCoordinatesShouldThrowException() {
        // Given
        new Area(null);

        // Then
        fail("Should throw exception");
    }
}