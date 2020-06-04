package com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories;

import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import com.github.pcavezzan.kubernetes.workshop.domain.MessageRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class MessageRepositoryFeignImpl implements MessageRepository {
    private final MessageStoreClient messageStoreClient;

    @Override
    public Message findById(String id) {
        log.info("Retrieving message ({}) from remote storage", id);
        try {
            final RemoteMessageStore remoteMessageStore = messageStoreClient.get(id);
            return new Message(remoteMessageStore.getKey(), remoteMessageStore.getValue());
        } catch (final FeignException feignException) {
            log.error("Could not retrieve message from remote storage", feignException);
            return null;
        }
    }

    @Override
    public String save(Message message) {
        log.info("Saving message ({}) to remote storage", message);
        try {
            messageStoreClient.put(message.key(), new RemoteMessageStore(message.key(), message.value()));
            return message.key();
        } catch (final FeignException feignException) {
            log.error("Could not save message to remote store", feignException);
            return null;
        }

    }
}
