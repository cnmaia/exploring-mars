package com.cnmaia.exploring.mars.domain.validation.area;

import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.validation.AbstractValidator;
import com.cnmaia.exploring.mars.domain.validation.Reason;
import com.cnmaia.exploring.mars.domain.validation.Validator;

/**
 * Created by cmaia on 9/27/17
 */
public class ExecuteHoversInstructionsValidator extends AbstractValidator<Area> {
    @Override
    public Validator<Area> validate(Area area) {
        if (area == null) {
            this.addReason(Reason.error("Cannot perform hover instructions if there's no area"));
            return this;
        }

        if (area.getHovers().isEmpty()) {
            this.addReason(Reason.error("Cannot perform hover instructions when there's no hover in area"));
        }

        return this;
    }
}
