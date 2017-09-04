package com.cnmaia.exploring.mars.resource.request;

import java.util.Set;

/**
 * Created by cmaia on 9/3/17
 */
public class ExploreRequestResource {
    private AreaRequestResource area;
    private Set<HoverRequestResource> hovers;

    public AreaRequestResource getArea() {
        return area;
    }

    public void setArea(AreaRequestResource area) {
        this.area = area;
    }

    public Set<HoverRequestResource> getHovers() {
        return hovers;
    }

    public void setHovers(Set<HoverRequestResource> hovers) {
        this.hovers = hovers;
    }
}
