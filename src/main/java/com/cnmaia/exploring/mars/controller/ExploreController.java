package com.cnmaia.exploring.mars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cnmaia.exploring.mars.controller.resource.request.ExploreRequestResource;
import com.cnmaia.exploring.mars.controller.resource.response.ExploreResponseResource;
import com.cnmaia.exploring.mars.service.ExploreService;

/**
 * Created by cmaia on 9/1/17
 */
@RestController
@RequestMapping("/explore")
public class ExploreController {

    private ExploreService exploreService;

    @Autowired
    public ExploreController(ExploreService exploreService) {
        this.exploreService = exploreService;
    }

    @RequestMapping(value = "/explore", method = RequestMethod.POST)
    public ResponseEntity<ExploreResponseResource> explore(@RequestBody ExploreRequestResource exploreRequestResource) {
        return null;
    }
}
