package com.cnmaia.exploring.mars.service;

import com.cnmaia.exploring.mars.resource.request.ExploreRequestResource;
import com.cnmaia.exploring.mars.resource.response.ExplorationResultResponseResource;

/**
 * Created by cmaia on 9/3/17.
 */
public interface ExploreService {
    ExplorationResultResponseResource explore(ExploreRequestResource exploreRequestResource);
}
