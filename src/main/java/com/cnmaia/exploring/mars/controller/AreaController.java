package com.cnmaia.exploring.mars.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cmaia on 9/1/17
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    //TODO Design a api
    //TODO how the controller must be named if the action will happen only once
    //TODO Define the resources
    //TODO Api documentation - swagger
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity doThings() {
        return null;
    }
}
