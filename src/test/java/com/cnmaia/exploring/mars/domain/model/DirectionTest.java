package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cmaia on 8/27/17.
 */
public class DirectionTest {

    @Test
    public void testDirectionsAreTheFourMainDirection() {
        // Given
        List<Direction> directions = Arrays.asList(Direction.values());

        // TODO Check this test
        // Then
        assertEquals(directions.size(), 4);
        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
        assertTrue(directions.contains(Direction.WEST));
    }

    @Test
    public void testDirectionAreArrangedInClockOrder() {
        // Given
        Direction[] directions = Direction.values();

        // Then
        assertEquals(directions[0], Direction.NORTH);
        assertEquals(directions[1], Direction.EAST);
        assertEquals(directions[2], Direction.SOUTH);
        assertEquals(directions[3], Direction.WEST);
    }

}