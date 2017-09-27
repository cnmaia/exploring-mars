package com.cnmaia.exploring.mars.domain.validation.area;

import com.cnmaia.exploring.mars.domain.model.Area;
import com.cnmaia.exploring.mars.domain.validation.AbstractValidator;
import com.cnmaia.exploring.mars.domain.validation.Reason;
import com.cnmaia.exploring.mars.domain.validation.Validator;

/**
 * Created by cmaia on 9/27/17
 */
public class DeployHoversAreaValidator extends AbstractValidator<Area> {

    @Override
    public Validator<Area> validate(Area area) {
        if (area == null) {
            this.addReason(Reason.error("Cannot deploy a hover to a null area"));
        }

        return this;
    }
}
