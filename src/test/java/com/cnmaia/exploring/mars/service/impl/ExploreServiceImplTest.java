package com.cnmaia.exploring.mars.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cnmaia.exploring.mars.domain.exception.ValidationException;
import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.service.AreaDomainService;
import com.cnmaia.exploring.mars.resource.HoverResource;
import com.cnmaia.exploring.mars.resource.request.AreaRequestResource;
import com.cnmaia.exploring.mars.resource.request.ExploreRequestResource;
import com.cnmaia.exploring.mars.resource.response.ExplorationResultResponseResource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cmaia on 9/4/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExploreServiceImplTest {

    @Mock
    private AreaDomainService areaDomainService;

    @InjectMocks
    private ExploreServiceImpl exploreService;

    @Test(expected = ValidationException.class)
    public void testExploreWithNullRequestShouldThrowException() {
        // When
        exploreService.explore(null);
    }

    @Test(expected = ValidationException.class)
    public void testExploreWithNullAreaShouldThrowException() {
        // Given
        ExploreRequestResource exploreRequestResource = new ExploreRequestResource();
        exploreRequestResource.setArea(null);
        exploreRequestResource.setHovers(new HashSet<>(Collections.singletonList(new HoverResource())));

        // When
        exploreService.explore(exploreRequestResource);
    }

    @Test(expected = ValidationException.class)
    public void testExploreWithNullHoversShouldThrowException() {
        // Given
        ExploreRequestResource exploreRequestResource = new ExploreRequestResource();
        exploreRequestResource.setArea(new AreaRequestResource());
        exploreRequestResource.setHovers(null);

        // When
        exploreService.explore(exploreRequestResource);
    }

    @Test(expected = ValidationException.class)
    public void testExploreWithEmptyHoversShouldThrowException() {
        // Given
        ExploreRequestResource exploreRequestResource = new ExploreRequestResource();
        exploreRequestResource.setArea(new AreaRequestResource());
        exploreRequestResource.setHovers(new HashSet<>());

        // When
        exploreService.explore(exploreRequestResource);
    }

    @Test
    public void testExploreWithSingleHover() {
        // Given
        ExploreRequestResource exploreRequestResource = new ExploreRequestResource();
        AreaRequestResource areaRequestResource = new AreaRequestResource();
        areaRequestResource.setUpperRightX(1);
        areaRequestResource.setUpperRightY(1);
        exploreRequestResource.setArea(areaRequestResource);
        Set<HoverResource> hoversResource = new HashSet<>();
        HoverResource curiosity = new HoverResource();
        curiosity.setY(0);
        curiosity.setX(0);
        curiosity.setName("curiosity");
        curiosity.setFacingDirection("N");
        curiosity.setInstructions(Collections.singletonList("M"));
        hoversResource.add(curiosity);
        exploreRequestResource.setHovers(hoversResource);

        Area areaToBeExplored = new Area(new Coordinate(1, 1));
        areaToBeExplored.addHover(new Hover("curiosity", new Coordinate(0, 0), Direction.NORTH));
        when(areaDomainService.deployHovers(any(Area.class), anySet())).thenReturn(areaToBeExplored);
        when(areaDomainService.executeHoversInstructions(any(Area.class))).thenReturn(areaToBeExplored);

        // When
        ExplorationResultResponseResource result = exploreService.explore(exploreRequestResource);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getHovers().size());
        assertEquals("curiosity", result.getHovers().get(0).getName());
        assertEquals("N", result.getHovers().get(0).getFacingDirection());
        assertEquals(0, result.getHovers().get(0).getX());
        assertEquals(0, result.getHovers().get(0).getY());
    }

    @Test
    public void testExploreWithMultiplesHovers() {
        ExploreRequestResource exploreRequestResource = new ExploreRequestResource();

        AreaRequestResource areaRequestResource = new AreaRequestResource();
        areaRequestResource.setUpperRightX(1);
        areaRequestResource.setUpperRightY(1);
        exploreRequestResource.setArea(areaRequestResource);

        Set<HoverResource> hoversResource = new HashSet<>();

        HoverResource curiosity = new HoverResource();
        curiosity.setY(0);
        curiosity.setX(0);
        curiosity.setName("curiosity");
        curiosity.setFacingDirection("N");
        curiosity.setInstructions(Collections.singletonList("M"));
        hoversResource.add(curiosity);

        HoverResource opportunity = new HoverResource();
        opportunity.setX(1);
        opportunity.setY(1);
        opportunity.setName("opportunity");
        opportunity.setFacingDirection("E");
        opportunity.setInstructions(Collections.singletonList("M"));
        hoversResource.add(opportunity);

        exploreRequestResource.setHovers(hoversResource);

        Area areaToBeExplored = new Area(new Coordinate(1, 1));
        areaToBeExplored.addHover(new Hover("curiosity", new Coordinate(0, 0), Direction.NORTH));
        areaToBeExplored.addHover(new Hover("opportunity", new Coordinate(1, 1), Direction.EAST));
        when(areaDomainService.deployHovers(any(Area.class), anySet())).thenReturn(areaToBeExplored);
        when(areaDomainService.executeHoversInstructions(any(Area.class))).thenReturn(areaToBeExplored);

        // When
        ExplorationResultResponseResource result = exploreService.explore(exploreRequestResource);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getHovers().size());

        assertEquals("curiosity", result.getHovers().get(0).getName());
        assertEquals("N", result.getHovers().get(0).getFacingDirection());
        assertEquals(0, result.getHovers().get(0).getX());
        assertEquals(0, result.getHovers().get(0).getY());

        assertEquals("opportunity", result.getHovers().get(1).getName());
        assertEquals("E", result.getHovers().get(1).getFacingDirection());
        assertEquals(1, result.getHovers().get(1).getX());
        assertEquals(1, result.getHovers().get(1).getY());
    }

}