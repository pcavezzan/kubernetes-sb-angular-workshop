package com.github.pcavezzan.kubernetes.workshop.infrastructure.kubernetes;

import com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories.MessageStoreClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
@Slf4j
public class MessageStoreVerifier {

    private final MessageStoreClient messageStoreClient;
    private final ApplicationEventPublisher eventPublisher;

    public boolean verify() {
        try {
            messageStoreClient.ping();
            return true;
        } catch (FeignException feignException) {
            log.error("Ping to remote message storage has failed");
            log.debug("The following error occurs while trying to ping remote storage:", feignException);
            AvailabilityChangeEvent.publish(this.eventPublisher, feignException, ReadinessState.REFUSING_TRAFFIC);
            return false;
        }
    }
}
