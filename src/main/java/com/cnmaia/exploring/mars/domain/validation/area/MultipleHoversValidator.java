package com.cnmaia.exploring.mars.domain.validation.area;

import com.cnmaia.exploring.mars.domain.model.Hover;
import com.cnmaia.exploring.mars.domain.validation.AbstractValidator;
import com.cnmaia.exploring.mars.domain.validation.Reason;
import com.cnmaia.exploring.mars.domain.validation.Validator;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by cmaia on 9/4/17
 */
public class MultipleHoversValidator extends AbstractValidator<Collection<Hover>> {

    @Override
    public Validator<Collection<Hover>> validate(Collection<Hover> hovers) {
        if (hovers == null) {
            this.addReason(Reason.error("Hovers collection cannot be null"));
            return this;
        }

        if (hovers.isEmpty()) {
            this.addReason(Reason.error("Hovers collection cannot be empty"));
        }

        // TODO - Check this, maybe another structure is mandatory
        if (hovers.stream().filter(Objects::isNull).count() == 1) {
            this.addReason(Reason.error("Hovers collection cannot contains null objects"));
        }

        return this;
    }
}
