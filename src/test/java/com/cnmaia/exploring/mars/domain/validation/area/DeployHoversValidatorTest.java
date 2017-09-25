package com.cnmaia.exploring.mars.domain.validation.area;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cnmaia.exploring.mars.domain.exception.ValidationException;
import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by cmaia on 9/4/17.
 */
public class DeployHoversValidatorTest {

    private static DeployHoversValidator deployHoversValidator;

    @BeforeClass
    public static void setUp() {
        deployHoversValidator = new DeployHoversValidator();
    }

    @Test
    public void testValidateSuccess() {
        // Given
        Set<Hover> hovers = new LinkedHashSet<>();
        hovers.add(new Hover("curiosity", new Coordinate(1 ,1), Direction.NORTH));

        // When
        deployHoversValidator.validate(hovers).verify();

        // Then
        // no-op
    }

    @Test(expected = ValidationException.class)
    public void testValidateWithNullHovers() {
        // When
        deployHoversValidator.validate(null).verify();
    }

    @Test(expected = ValidationException.class)
    public void testValidateWithEmptyHovers() {
        // Given
        Set<Hover> hovers = new LinkedHashSet<>();

        // When
        deployHoversValidator.validate(hovers).verify();
    }

    @Test(expected = ValidationException.class)
    public void testValidateWithHoverCollectionContainingNullObject() {
        // Given
        Set<Hover> hovers = new LinkedHashSet<>();
        hovers.add(null);

        // When
        deployHoversValidator.validate(hovers).verify();
    }

    @Test(expected = ValidationException.class)
    public void testValidateWithDuplicateHoversNames() {
        // Given
        Set<Hover> hovers = new LinkedHashSet<>();
        hovers.add(new Hover("curiosity", new Coordinate(1 ,1), Direction.NORTH));
        hovers.add(new Hover("curiosity", new Coordinate(1 ,1), Direction.NORTH));

        // When
        deployHoversValidator.validate(hovers).verify();
    }
}