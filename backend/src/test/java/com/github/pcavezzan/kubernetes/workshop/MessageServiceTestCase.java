package com.github.pcavezzan.kubernetes.workshop;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageServiceTestCase {

    private final MessageService messageService = new MessageService("Foo Bar",
            "localhost",
            "v1 build on 2020-05-21",
            "running v1 on localhost");

    @Test
    void getWelcomeMsg() {
        assertThat(messageService.getWelcomeMsg()).isEqualTo(new Message("Foo Bar"));
    }

    @Test
    void getServerHostName() {
        assertThat(messageService.getServerHostName()).isEqualTo(new Message("localhost"));
    }

    @Test
    void getBuildInfo() {
        assertThat(messageService.getBuildInfo()).isEqualTo(new Message("v1 build on 2020-05-21"));
    }

    @Test
    void getInfo() {
        assertThat(messageService.getInfo()).isEqualTo(new Message("running v1 on localhost"));
    }
}