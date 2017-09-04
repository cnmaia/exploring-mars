package com.cnmaia.exploring.mars.domain.exception;

/**
 * Created by cmaia on 9/4/17
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
