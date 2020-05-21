package com.github.pcavezzan.kubernetes.workshop;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WhoamiResourceITCase {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getShouldReturnLocalhost() throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(get("/whoami"));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"payload\":\"localhost\"}")));
    }

}
