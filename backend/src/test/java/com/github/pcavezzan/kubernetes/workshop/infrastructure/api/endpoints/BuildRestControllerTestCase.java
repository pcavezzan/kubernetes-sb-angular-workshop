package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.endpoints;


import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.resources.MessageResource;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.services.MessageResourceService;
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

@WebMvcTest(BuildRestController.class)
public class BuildRestControllerTestCase extends AbstractRestControllerTestCase {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageResourceService service;

    @Test
    public void getShouldReturnHelloWorld() throws Exception {
        when(service.getBuildInfo()).thenReturn(new MessageResource("v1 on 2020-05-15"));

        final ResultActions resultActions = this.mockMvc.perform(get("/build"));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"payload\":\"v1 on 2020-05-15\"}")));
    }

}
