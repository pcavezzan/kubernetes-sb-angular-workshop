package com.github.pcavezzan.kubernetes.workshop.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTestCase {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    void getWelcomeMsg() {
        when(messageRepository.findById("welcome")).thenReturn(new Message("welcome", "Foo Bar"));

        final Message msg = messageService.getWelcomeMsg();

        assertThat(msg).isEqualTo(new Message("welcome", "Foo Bar"));
    }

    @Test
    void getServerHostName() {
        when(messageRepository.findById("server_host_name")).thenReturn(new Message("server_host_name", "localhost"));

        final Message message = messageService.getServerHostName();

        assertThat(message).isEqualTo(new Message("server_host_name", "localhost"));
    }

    @Test
    void getBuildInfo() {
        when(messageRepository.findById("build_info")).thenReturn(new Message("build_info", "v1 build on 2020-05-21"));

        final Message message = messageService.getBuildInfo();

        assertThat(message).isEqualTo(new Message("build_info", "v1 build on 2020-05-21"));
    }

    @Test
    void getInfo() {
        when(messageRepository.findById("info")).thenReturn(new Message("info", "running v1 on localhost"));

        final Message message = messageService.getInfo();

        assertThat(message).isEqualTo(new Message("info", "running v1 on localhost"));
    }

    @Test
    void findByIdShouldReturnMessageWithValueWhenIdExists() {
        when(messageRepository.findById("key")).thenReturn(new Message("key", "value"));

        final Message message = messageService.findById("key");

        assertThat(message).isEqualTo(new Message("key", "value"));
    }

    @Test
    void findByIdShouldReturnMessageWithEmptyValueWhenIdDoesNotExist() {
        final Message message = messageService.findById("key");

        assertThat(message).isEqualTo(new Message("key", ""));
    }
}