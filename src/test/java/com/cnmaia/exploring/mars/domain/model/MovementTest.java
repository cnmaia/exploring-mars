package com.cnmaia.exploring.mars.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Created by cmaia on 9/25/17.
 */
public class MovementTest {

    @Test
    public void testMovementsFromValueShouldReturnRightMovement() {
        // Given
        char movement = 'M';

        // When
        Movement result = Movement.fromValue(movement);

        // Then
        assertNotNull(result);
        assertEquals(Movement.MOVE, result);
    }

}