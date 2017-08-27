package com.cnmaia.exploring.mars.domain.exception;

import com.cnmaia.exploring.mars.domain.model.Hover;

import java.util.Set;

/**
 * Created by cmaia on 8/27/17
 */
public class HoverCollisionException extends RuntimeException {
    private Set<Hover> hoversInvolved;

    // TODO Remove this constructor when hover get a unique name
    public HoverCollisionException(String message) {
        super(message);
    }

    public HoverCollisionException(Set<Hover> hoversInvolved, String message) {
        super(message);

        validateHovers(hoversInvolved);
        this.hoversInvolved = hoversInvolved;
    }

    public HoverCollisionException(Set<Hover> hoversInvolved, String message, Throwable cause) {
        super(message, cause);

        validateHovers(hoversInvolved);
        this.hoversInvolved = hoversInvolved;
    }

    public Set<Hover> getHoversInvolved() {
        return hoversInvolved;
    }

    private void validateHovers(Set<Hover> hovers) {
        if (hovers == null || hovers.isEmpty()) {
            throw new IllegalArgumentException("Colliding hovers cannot be null or empty");
        }
    }
}
