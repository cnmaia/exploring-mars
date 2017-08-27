package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cnmaia.exploring.mars.domain.exception.HoverCollisionException;

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

    @Test
    public void testCreateNewAreaShouldNotHaveHovers() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // Then
        assertNotNull(area.getHovers());
        assertTrue(area.getHovers().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertNullHoverInAreaShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // When
        area.addHover(null);

        // Then
        fail("Should throw exception");
    }

//    @Test
//    public void testInsertNewHoversMaintainsInsertionOrder() {
//        // Given
//        Area area = new Area(new Coordinate(2 ,2));
//
//        // When
//        area.addHover(new Hover(new Coordinate(1, 1), Direction.NORTH));
//        area.addHover(new Hover(new Coordinate(2, 2), Direction.NORTH));
//
//        // Then
//        fail("Do not know how to implement this yet");
//    }

    @Test(expected = HoverCollisionException.class)
    public void testInsertHoverInSameLocationShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // When
        area.addHover(new Hover(new Coordinate(1, 1), Direction.NORTH));
        area.addHover(new Hover(new Coordinate(1, 1), Direction.NORTH));

        // Then
        fail("Should throw exception");
    }
}