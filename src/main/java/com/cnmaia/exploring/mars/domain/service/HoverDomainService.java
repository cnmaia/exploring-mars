package com.cnmaia.exploring.mars.domain.service;

import com.cnmaia.exploring.mars.domain.model.Hover;

/**
 * Created by cmaia on 9/6/17.
 */
public interface HoverDomainService {
    Hover executeNextInstruction(Hover hover);
    Hover executeAllLeftInstructions(Hover hover);
}
