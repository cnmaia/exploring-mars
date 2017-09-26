package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cmaia on 8/27/17.
 */
public class DirectionTest {

    @Test
    public void testDirectionsAreTheFourMainDirection() {
        // Given
        List<Direction> directions = Arrays.asList(Direction.values());
        List<String> directionsValues = directions.stream().map(d -> String.valueOf(d.getValue())).collect(Collectors.toList());

        // Then
        assertEquals(directions.size(), 4);
        assertTrue(directionsValues.contains("N"));
        assertTrue(directionsValues.contains("S"));
        assertTrue(directionsValues.contains("E"));
        assertTrue(directionsValues.contains("W"));
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

    @Test
    public void testDirectionValueShouldBeTheFirstLetterOfTheDirection() {
        // Given
        Direction[] directions = Direction.values();

        // Then
        assertEquals(directions[0].getValue(), 'N');
        assertEquals(directions[1].getValue(), 'E');
        assertEquals(directions[2].getValue(), 'S');
        assertEquals(directions[3].getValue(), 'W');
    }

    @Test
    public void testDirectionFromValueShouldReturnRightDirection() {
        // Given
        char direction = 'N';

        // When
        Direction result = Direction.fromValue(direction);

        // Then
        assertNotNull(result);
        assertEquals(Direction.NORTH, result);
    }
}