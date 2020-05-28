package com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories;

import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageRepositoryFeignImplTestCase {

    @Mock
    private MessageStoreClient messageStoreClient;

    @InjectMocks
    private MessageRepositoryFeignImpl messageRepositoryFeign;

    private final String id = "myId";

    @Test
    void findByIdShouldReturnMessageWhenIdExists() {
        when(messageStoreClient.get(id)).thenReturn(new Message(id, "value"));

        final Message message = messageRepositoryFeign.findById(id);

        assertThat(message).isEqualTo(new Message(id, "value"));
    }

    @Test
    void findByIdShouldReturnNullWhenIdDoesNotExist() {
        final Message message = messageRepositoryFeign.findById(id);

        assertThat(message).isNull();
    }

    @Test
    void saveShouldReturnMessageKeyWhenFeignCallSucceed() {
        final Message message = new Message(id, "value");

        final String result = messageRepositoryFeign.save(message);

        assertThat(result).isEqualTo(id);
    }

    @Test
    void saveShouldCallPutMessageToRemoteStorage() {
        final Message message = new Message(id, "value");

        final String result = messageRepositoryFeign.save(message);

        verify(messageStoreClient).put(id, message);
    }

    @Test
    void saveShouldReturnNullWhenRemoteStorageCallFails() {
        final Message message = new Message(id, "value");
        doThrow(FeignException.NotFound.class).when(messageStoreClient).put(id, message);

        final String result = messageRepositoryFeign.save(message);

        assertThat(result).isNull();
    }

}