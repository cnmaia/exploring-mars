package com.cnmaia.exploring.mars.domain.service.impl;

import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.service.AreaDomainService;
import com.cnmaia.exploring.mars.domain.service.HoverDomainService;
import com.cnmaia.exploring.mars.domain.validation.area.DeployHoversAreaValidator;
import com.cnmaia.exploring.mars.domain.validation.area.DeployHoversValidator;

import java.util.Collections;
import java.util.Set;

/**
 * Created by cmaia on 8/27/17
 */
public class AreaDomainServiceImpl implements AreaDomainService {

    private final HoverDomainService hoverDomainService;

    public AreaDomainServiceImpl(HoverDomainService hoverDomainService) {
        this.hoverDomainService = hoverDomainService;
    }

    @Override
    public Area deployHover(final Area area, final Hover hover) {
        return this.deployHovers(area, Collections.singleton(hover));
    }

    @Override
    public Area deployHovers(final Area area, final Set<Hover> hovers) {
        new DeployHoversAreaValidator().validate(area).verify();
        new DeployHoversValidator().validate(hovers).verify();

        hovers.forEach(area::addHover);

        return area;
    }

    @Override
    public Area executeHoversInstructions(final Area area) {
        if (area == null) {
            throw new IllegalArgumentException("Cannot perform hover instructions if there's no area");
        }

        if (area.getHovers().isEmpty()) {
            throw new IllegalStateException("Cannot perform hover instructions when there's no hover in area");
        }

        for (Hover hover : area.getHovers()) {
            Hover newHoverState = hoverDomainService.executeAllLeftInstructions(hover);
            area.addHover(newHoverState);
        }

        return area;
    }
}
