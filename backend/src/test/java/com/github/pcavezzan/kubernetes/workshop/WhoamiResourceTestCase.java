package com.github.pcavezzan.kubernetes.workshop;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WhoamiResource.class)
public class WhoamiResourceTestCase {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService service;

    @Test
    public void getShouldReturnHelloWorld() throws Exception {
        when(service.getServerHostName()).thenReturn(new Message("localhost"));

        final ResultActions resultActions = this.mockMvc.perform(get("/whoami"));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"payload\":\"localhost\"}")));
    }

}
