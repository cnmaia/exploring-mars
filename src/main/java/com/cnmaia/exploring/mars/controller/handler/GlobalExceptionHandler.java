package com.cnmaia.exploring.mars.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cnmaia.exploring.mars.controller.resource.ErrorResource;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cmaia on 9/27/17
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResource> handleIllegalArgumentException() {
        return new ResponseEntity<>(new ErrorResource("Invalid parameters, please try again."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResource> handleMessageNotReadableException() {
        return new ResponseEntity<>(new ErrorResource("Invalid parameters, please try again with correct types."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResource> handleMethodNotSupported() {
        return new ResponseEntity<>(new ErrorResource("Http method not supported for this operation, " +
                "please check api documentation and try again with correct method."), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResource> handleMediaNotSupported(HttpMediaTypeNotSupportedException e) {
        return new ResponseEntity<>(new ErrorResource("Http media type not supported for this operation, " +
                "please check api documentation and try again with correct type." + e.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResource> handleGenericException(HttpServletRequest request, Exception ex) {
        LOGGER.error("url='{}'", request.getRequestURI(), ex);
        return new ResponseEntity<>(new ErrorResource("An error occurred in server, please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
