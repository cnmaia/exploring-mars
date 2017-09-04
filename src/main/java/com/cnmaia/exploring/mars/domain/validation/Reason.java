package com.cnmaia.exploring.mars.domain.validation;

/**
 * Created by cmaia on 9/4/17
 */
public class Reason {
    private String message;

    private Reason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Reason error(String message) {
        return new Reason(message);
    }
}
