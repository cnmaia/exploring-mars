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
        area.addHover(new Hover("XYZ", new Coordinate(1, 1), Direction.NORTH));
        area.addHover(new Hover("ABC", new Coordinate(1, 1), Direction.NORTH));

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testHoverInLocationBeyondUpperAreaBoundary() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // When
        area.addHover(new Hover("XYZ", new Coordinate(1, 2), Direction.EAST));

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testHoverInLocationBeyondRightAreaBoundary() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // When
        area.addHover(new Hover("XYZ", new Coordinate(2, 1), Direction.EAST));

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testHoverInLocationBeyondLowerAreaBoundary() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // When
        area.addHover(new Hover("XYZ", new Coordinate(1, -1), Direction.EAST));

        // Then
        fail("Should throw exception");
    }

    @Test(expected = HoverCollisionException.class)
    public void testHoverInLocationBeyondLeftAreaBoundary() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // When
        area.addHover(new Hover("XYZ", new Coordinate(-1, 1), Direction.EAST));

        // Then
        fail("Should throw exception");
    }

    @Test
    public void testAddHoversWithSameNameShouldOverride() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // When
        area.addHover(new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH));
        area.addHover(new Hover("Curiosity", new Coordinate(1, 1), Direction.NORTH));

        // Then
        assertNotNull(area.getHovers());
        assertEquals(1, area.getHovers().size());
        assertEquals(new Coordinate(1, 1), area.getHovers().stream().findFirst().get().getCurrentLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullHoverShouldThrowException() {
        // Given
        Area area = new Area(new Coordinate(1, 1));

        // When
        area.updateHover(null);

        // then
        fail("Should throw exception");
    }

    @Test
    public void testUpdateHoverOldHoverShouldHaveNewValue() {
        // Given
        Area area = new Area(new Coordinate(1, 1));
        Hover hover = new Hover("Curiosity", new Coordinate(0, 0), Direction.NORTH);

        // When
        area.addHover(hover);
        assertEquals(0, area.getHovers().stream().findFirst().get().getInstructionHistory().size());

        hover.addInstruction(new LeftRotateInstruction());
        area.updateHover(hover);

        // Then
        assertEquals(1, area.getHovers().stream().findFirst().get().getInstructionHistory().size());
        assertEquals(Movement.LEFT, area.getHovers().stream().findFirst().get().getInstructionHistory().get(0));
    }
}