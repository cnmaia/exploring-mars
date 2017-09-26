package com.cnmaia.exploring.mars.domain.validation.area;

import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.validation.AbstractValidator;
import com.cnmaia.exploring.mars.domain.validation.Reason;
import com.cnmaia.exploring.mars.domain.validation.Validator;

import java.util.Set;

/**
 * Created by cmaia on 9/4/17
 */
public class DeployHoversValidator extends AbstractValidator<Set<Hover>> {

    @Override
    public Validator<Set<Hover>> validate(Set<Hover> hovers) {
        if (hovers == null) {
            this.addReason(Reason.error("Hovers collection cannot be null"));
            return this;
        }

        if (hovers.isEmpty()) {
            this.addReason(Reason.error("Hovers collection cannot be empty"));
        }

        // TODO - Check this, maybe another structure is mandatory
        if (hovers.contains(null)) {
            this.addReason(Reason.error("Hovers collection cannot contains null objects"));
        }

        return this;
    }
}
