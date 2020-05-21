package com.github.pcavezzan.kubernetes.workshop;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WelcomeResource.class)
public class WelcomeResourceTestCase {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService service;

    @Test
    public void getShouldReturnHelloWorld() throws Exception {
        when(service.getWelcomeMsg()).thenReturn(new Message("Hello World"));

        final ResultActions resultActions = this.mockMvc.perform(get("/welcome"));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"payload\":\"Hello World\"}")));
    }

}
