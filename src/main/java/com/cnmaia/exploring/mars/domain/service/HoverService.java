package com.cnmaia.exploring.mars.domain.service;

import com.cnmaia.exploring.mars.domain.model.Coordinate;
import com.cnmaia.exploring.mars.domain.model.Direction;
import com.cnmaia.exploring.mars.domain.model.Hover;

/**
 * Created by cmaia on 8/27/17.
 */
public interface HoverService {
    Hover deploy(Coordinate initialLocation, Direction initialFacingDirection);
}
