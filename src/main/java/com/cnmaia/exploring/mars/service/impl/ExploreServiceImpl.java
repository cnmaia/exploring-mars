package com.cnmaia.exploring.mars.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.service.AreaDomainService;
import com.cnmaia.exploring.mars.resource.HoverResource;
import com.cnmaia.exploring.mars.resource.request.ExploreRequestResource;
import com.cnmaia.exploring.mars.resource.response.ExplorationResultResponseResource;
import com.cnmaia.exploring.mars.service.ExploreService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cmaia on 9/3/17
 */
@Service
public class ExploreServiceImpl implements ExploreService {

    private AreaDomainService areaDomainService;

    @Autowired
    public ExploreServiceImpl(AreaDomainService areaDomainService) {
        this.areaDomainService = areaDomainService;
    }

    @Override
    public ExplorationResultResponseResource explore(ExploreRequestResource exploreRequest) {
        // validate
        if (exploreRequest == null) {
            throw new IllegalArgumentException("Cannot explore something that does not exist! (null)");
        }
        if (exploreRequest.getArea() == null) {
            throw new IllegalArgumentException("Cannot explore without an area!");
        }

        if (exploreRequest.getHovers() == null || exploreRequest.getHovers().isEmpty()) {
            throw new IllegalArgumentException("Cannot explore without hovers!");
        }

        // call service
        Area areaToBeExplored = new Area(new Coordinate(exploreRequest.getArea().getUpperRightX(), exploreRequest.getArea().getUpperRightY()));
        Set<Hover> explorationHovers = exploreRequest.getHovers().stream().map(h -> new Hover(h.getName(), new Coordinate(h.getX(), h.getY()), Direction.fromValue(h.getFacingDirection()))).collect(Collectors.toSet());

        areaToBeExplored = this.areaDomainService.deployHovers(areaToBeExplored, explorationHovers);
        Area areaExplored = this.areaDomainService.executeHoversInstructions(areaToBeExplored);

        // convert response
        return new ExplorationResultResponseResource(areaExplored.getHovers().stream().map(h -> {
            HoverResource hover = new HoverResource();

            hover.setFacingDirection(h.getFacingDirection().getValue());
            hover.setName(h.getName());
            hover.setX(h.getCurrentLocation().getX());
            hover.setY(h.getCurrentLocation().getY());

            return hover;
        }).collect(Collectors.toList()));
    }
}
