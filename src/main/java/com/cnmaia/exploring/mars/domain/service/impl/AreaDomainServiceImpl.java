package com.cnmaia.exploring.mars.domain.service.impl;

import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.model.Instruction;
import com.cnmaia.exploring.mars.domain.service.AreaDomainService;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by cmaia on 8/27/17
 */
public class AreaDomainServiceImpl implements AreaDomainService {

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
        if (area == null) {
            throw new IllegalArgumentException("Cannot perform hover instructions if there's no area");
        }

        if (area.getHovers().isEmpty()) {
            throw new IllegalStateException("Cannot perform hover instructions when there's no hover in area");
        }

        Set<Hover> copyHovers = new LinkedHashSet<>(area.getHovers());
        for (Hover h : copyHovers) {
            for (Instruction instruction : h.getInstructionHistory()) {
                Hover hover = h.executeNextInstruction();
                area.updateHover(hover);
            }
        }

        return area;
    }
}
