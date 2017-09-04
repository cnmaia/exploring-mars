package com.cnmaia.exploring.mars.resource.response;

import com.cnmaia.exploring.mars.resource.HoverResource;

import java.util.List;

/**
 * Created by cmaia on 9/3/17
 */
public class ExplorationResultResponseResource {
    private final List<HoverResource> hovers;

    public ExplorationResultResponseResource(List<HoverResource> hovers) {
        this.hovers = hovers;
    }

    public List<HoverResource> getHovers() {
        return hovers;
    }
}
