package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.services;

import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import com.github.pcavezzan.kubernetes.workshop.domain.MessageService;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.resources.MessageResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageResourceServiceTestCase {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageResourceService messageResourceService;

    @Test
    public void getWelcomeShouldReturnWelcomeFromMessageService() {
        when(messageService.getWelcomeMsg()).thenReturn(new Message("welcome", "Hello World"));

        final MessageResource messageResource = messageResourceService.getWelcome();

        assertThat(messageResource).isEqualTo(new MessageResource("Hello World"));
    }

    @Test
    public void getBuildInfoShouldReturnBuildInfoFromMessageService() {
        when(messageService.getBuildInfo()).thenReturn(new Message("build_info", "v1 on 2020-05-15"));

        final MessageResource messageResource = messageResourceService.getBuildInfo();

        assertThat(messageResource).isEqualTo(new MessageResource("v1 on 2020-05-15"));
    }


    @Test
    public void getInfoShouldReturnInfoFromMessageService() {
        when(messageService.getInfo()).thenReturn(new Message("info", "running v1 on 2020-05-15"));

        final MessageResource messageResource = messageResourceService.getInfo();

        assertThat(messageResource).isEqualTo(new MessageResource("running v1 on 2020-05-15"));

    }


    @Test
    public void getWhoamiShouldReturnWhoamiFromMessageService() {
        when(messageService.getServerHostName()).thenReturn(new Message("server_host_name", "localhost"));

        final MessageResource messageResource = messageResourceService.getWhoami();

        assertThat(messageResource).isEqualTo(new MessageResource("localhost"));
    }

}
