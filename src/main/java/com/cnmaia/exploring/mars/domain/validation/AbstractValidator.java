package com.cnmaia.exploring.mars.domain.validation;

import com.cnmaia.exploring.mars.domain.exception.ValidationException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cmaia on 9/4/17
 */
public abstract class AbstractValidator<T> implements Validator<T> {
    private Set<Reason> reasons = new HashSet<>();

    public void verify() {
        if (!reasons.isEmpty()) {
            throw new ValidationException(reasons.stream().map(Reason::getMessage).collect(Collectors.joining(", ")));
        }
    }

    protected void addReason(Reason reason) {
        this.reasons.add(reason);
    }

    protected void mergeReasons(Collection<Reason> reasons) {
        this.reasons.addAll(reasons);
    }

    @Override
    public Collection<Reason> getReasons() {
        return this.reasons;
    }

    @Override
    public Validator<T> validate(T instance) {
        // no-op
        return this;
    }
}
