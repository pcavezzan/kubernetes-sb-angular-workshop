package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.endpoints;


import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BuildRestControllerITCase extends AbstractRestControllerITCase {

    @Test
    public void getShouldReturnHelloWorld() throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(get("/build"));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"payload\":\"vTest\"}")));
    }

}
