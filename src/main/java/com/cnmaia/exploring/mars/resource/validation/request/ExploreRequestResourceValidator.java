package com.cnmaia.exploring.mars.resource.validation.request;

import com.cnmaia.exploring.mars.domain.validation.AbstractValidator;
import com.cnmaia.exploring.mars.domain.validation.Reason;
import com.cnmaia.exploring.mars.domain.validation.Validator;
import com.cnmaia.exploring.mars.resource.request.ExploreRequestResource;

/**
 * Created by cmaia on 9/25/17
 */
public class ExploreRequestResourceValidator extends AbstractValidator<ExploreRequestResource> {
    @Override
    public Validator<ExploreRequestResource> validate(ExploreRequestResource resource) {
        if (resource == null) {
            this.addReason(Reason.error("Exploration cannot be null"));
            return this;
        }

        if (resource.getArea() == null) {
            this.addReason(Reason.error("Exploration area cannot be null"));
        }

        if (resource.getHovers() == null || resource.getHovers().isEmpty()) {
            this.addReason(Reason.error("Exploration area must have at least 1 hover"));
            return this;
        }

        return this;
    }
}
