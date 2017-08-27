package com.cnmaia.exploring.mars.domain.service;

import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Coordinate;

/**
 * Created by cmaia on 8/27/17.
 */
public interface AreaService {
    Area create(Coordinate upperRightCoordinate);
}
