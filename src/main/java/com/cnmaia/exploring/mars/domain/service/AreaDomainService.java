package com.cnmaia.exploring.mars.domain.service;

import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Hover;

import java.util.Set;

/**
 * Created by cmaia on 8/27/17.
 */
public interface AreaDomainService {
    Area deployHover(Area area, Hover hover);
    Area deployHovers(Area area, Set<Hover> hovers);
    Area executeHoversInstructions(Area area);
}
