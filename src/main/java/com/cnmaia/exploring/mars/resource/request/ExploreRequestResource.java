package com.cnmaia.exploring.mars.resource.request;

import com.cnmaia.exploring.mars.resource.HoverResource;

import java.util.List;

/**
 * Created by cmaia on 9/3/17
 */
public class ExploreRequestResource {
    private AreaRequestResource area;
    private List<HoverResource> hovers;

    public AreaRequestResource getArea() {
        return area;
    }

    public void setArea(AreaRequestResource area) {
        this.area = area;
    }

    public List<HoverResource> getHovers() {
        return hovers;
    }

    public void setHovers(List<HoverResource> hovers) {
        this.hovers = hovers;
    }
}
