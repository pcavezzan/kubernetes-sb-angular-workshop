package com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories;

import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageRepositoryFeignImplTestCase {

    private final String id = "myId";
    @Mock
    private MessageStoreClient messageStoreClient;
    @InjectMocks
    private MessageRepositoryFeignImpl messageRepositoryFeign;

    @Test
    void findByIdShouldReturnMessageWhenIdExists() {
        when(messageStoreClient.get(id)).thenReturn(new RemoteMessageStore(id, "value"));

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

        verify(messageStoreClient).put(id, new RemoteMessageStore(message.key(), message.value()));
    }

    @Test
    void saveShouldReturnNullWhenRemoteStorageCallFails() {
        final Message message = new Message(id, "value");
        doThrow(FeignException.NotFound.class).when(messageStoreClient).put(id, new RemoteMessageStore(message.key(), message.value()));

        final String result = messageRepositoryFeign.save(message);

        assertThat(result).isNull();
    }

}