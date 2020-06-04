package com.github.pcavezzan.kubernetes.workshop.domain;

import com.github.pcavezzan.kubernetes.workshop.AbstractITCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class MessageServiceITCase extends AbstractITCase {

    @Autowired
    private MessageService messageService;

    @Test
    void getWelcomeMsgShouldReturnHelloWorld() {
        final Message welcomeMsg = messageService.getWelcomeMsg();

        assertThat(welcomeMsg).isEqualTo(new Message("welcome", "** Hello World **"));
    }

    @Test
    void getServerHostNameShouldReturnLocalhost() {
        final Message serverHostName = messageService.getServerHostName();

        assertThat(serverHostName).isEqualTo(new Message("server_host_name", "localhost"));
    }

    @Test
    void getBuildInfoShouldReturnVersionLatestInDev() {
        final Message serverHostName = messageService.getBuildInfo();

        assertThat(serverHostName).isEqualTo(new Message("build_info", "vTest"));
    }

    @Test
    void getInfoShouldReturnInfoMessage() {
        final Message serverHostName = messageService.getInfo();

        assertThat(serverHostName).isEqualTo(new Message("info", "vTest running on localhost"));
    }

    @Test
    void findByIdShouldReturnMessageWithValue() {
        final Message message = messageService.findById("info");

        assertThat(message).isEqualTo(new Message("info", "vTest running on localhost"));
    }

    @Test
    void findByIdShouldReturnMessageWithEmptyValue() {
        final Message message = messageService.findById("doesNotExist");

        assertThat(message).isEqualTo(new Message("doesNotExist", ""));
    }
}