package com.cnmaia.exploring.mars.controller.resource;

/**
 * Created by cmaia on 9/27/17
 */
public class ErrorResource {
    private final String message;

    public ErrorResource(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
