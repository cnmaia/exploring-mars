package com.cnmaia.exploring.mars.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cnmaia.exploring.mars.domain.service.AreaDomainService;
import com.cnmaia.exploring.mars.domain.service.impl.AreaDomainServiceImpl;

/**
 * Created by cmaia on 9/4/17
 */
@Configuration
public class DomainConfiguration {

    @Bean
    public AreaDomainService setupAreaService() {
        return new AreaDomainServiceImpl();
    }
}
