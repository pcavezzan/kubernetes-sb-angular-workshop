package com.github.pcavezzan.kubernetes.workshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MessageServiceITCase {

    @Autowired
    private MessageService messageService;

    @Test
    void getWelcomeMsgShouldReturnHelloWorld() {
        final Message welcomeMsg = messageService.getWelcomeMsg();
        assertThat(welcomeMsg).isEqualTo(new Message("** Hello World **"));
    }

    @Test
    void getServerHostNameShouldReturnLocalhost() {
        final Message serverHostName = messageService.getServerHostName();
        assertThat(serverHostName).isEqualTo(new Message("localhost"));
    }
}