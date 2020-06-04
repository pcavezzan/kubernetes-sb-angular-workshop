package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.endpoints;

import com.github.pcavezzan.kubernetes.workshop.AbstractITCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
abstract class AbstractRestControllerITCase extends AbstractITCase {

    @Autowired
    protected MockMvc mockMvc;

}
