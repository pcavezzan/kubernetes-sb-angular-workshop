package com.github.pcavezzan.kubernetes.workshop;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageServiceTestCase {

    private final MessageService messageService = new MessageService("Foo Bar", "localhost");

    @Test
    void getWelcomeMsg() {
        assertThat(messageService.getWelcomeMsg()).isEqualTo(new Message("Foo Bar"));
    }

    @Test
    void getServerHostName() {
        assertThat(messageService.getServerHostName()).isEqualTo(new Message("localhost"));
    }
}