package com.cnmaia.exploring.mars.resource.request;

import com.cnmaia.exploring.mars.resource.HoverResource;

import java.util.Set;

/**
 * Created by cmaia on 9/3/17
 */
public class ExploreRequestResource {
    private AreaRequestResource area;
    private Set<HoverResource> hovers;

    public AreaRequestResource getArea() {
        return area;
    }

    public void setArea(AreaRequestResource area) {
        this.area = area;
    }

    public Set<HoverResource> getHovers() {
        return hovers;
    }

    public void setHovers(Set<HoverResource> hovers) {
        this.hovers = hovers;
    }
}
