package com.cnmaia.exploring.mars.domain.service.impl;

import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.service.AreaService;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Created by cmaia on 8/27/17
 */
public class AreaServiceImpl implements AreaService {

    @Override
    public Area deployHover(final Area area, final Hover hover) {
        return this.deployHovers(area, Collections.singleton(hover));
    }

    @Override
    public Area deployHovers(final Area area, final Set<Hover> hovers) {
        if (area == null) {
            throw new IllegalArgumentException("Cannot deploy a hover to a null area");
        }

        if (hovers == null || hovers.isEmpty() || hovers.stream().filter(Objects::isNull).count() == 1) {
            throw new IllegalArgumentException("Cannot deploy a null hover to an area");
        }

        hovers.forEach(area::addHover);

        return area;
    }

    @Override
    public Area executeHoversInstructions(final Area area) {
        if (area.getHovers().isEmpty()) {
            throw new IllegalStateException("Cannot perform hover instructions when there's no hover in area");
        }

        area.getHovers().forEach(Hover::performInstructions);

        return area;
    }
}
