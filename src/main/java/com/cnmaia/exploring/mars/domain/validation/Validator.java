package com.cnmaia.exploring.mars.domain.validation;

import java.util.Collection;

/**
 * Created by cmaia on 9/4/17.
 */
public interface Validator<T> {
    Validator<T> validate(T instance);
    void verify();
    Collection<Reason> getReasons();
}
