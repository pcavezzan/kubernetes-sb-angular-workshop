package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.endpoints;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WelcomeRestControllerITCase extends AbstractRestControllerITCase {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getShouldReturnHelloWorld() throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(get("/welcome"));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"payload\":\"** Hello World **\"}")));
    }

}
