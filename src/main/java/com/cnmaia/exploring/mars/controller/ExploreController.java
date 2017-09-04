package com.cnmaia.exploring.mars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cnmaia.exploring.mars.resource.request.ExploreRequestResource;
import com.cnmaia.exploring.mars.resource.response.ExplorationResultResponseResource;
import com.cnmaia.exploring.mars.service.ExploreService;

/**
 * Created by cmaia on 9/1/17
 */
@RestController
@RequestMapping("/explore")
public class ExploreController {

    private final ExploreService exploreService;

    @Autowired
    public ExploreController(ExploreService exploreService) {
        this.exploreService = exploreService;
    }

    @RequestMapping(value = { "", "/" }, method = RequestMethod.POST)
    public ResponseEntity<ExplorationResultResponseResource> explore(@RequestBody ExploreRequestResource exploreRequestResource) {
        ExplorationResultResponseResource result = exploreService.explore(exploreRequestResource);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
