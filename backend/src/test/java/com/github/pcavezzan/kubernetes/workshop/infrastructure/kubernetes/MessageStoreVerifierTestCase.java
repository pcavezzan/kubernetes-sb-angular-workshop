package com.github.pcavezzan.kubernetes.workshop.infrastructure.kubernetes;

import com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories.MessageStoreClient;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageStoreVerifierTestCase {

    @Mock
    private MessageStoreClient messageStoreClient;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private MessageStoreVerifier messageStoreVerifier;

    @Test
    void verifyShouldReturnTrueWhenPingWorks() {
        final boolean result = messageStoreVerifier.verify();

        assertThat(result).isTrue();
        verify(messageStoreClient).ping();
    }

    @Test
    void verifyShouldReturnFalseWhenPingDoesNotWork() {
        doThrow(FeignException.class).when(messageStoreClient).ping();

        final boolean result = messageStoreVerifier.verify();

        assertThat(result).isFalse();
    }

    @Test
    void verifyShouldPublishReadinessRefuseTrafficWhenPingDoesNotWork() {
        final Request request = Request.create(Request.HttpMethod.GET, "http://store:9090/ping", Map.of(), new byte[]{}, StandardCharsets.UTF_8, new RequestTemplate());
        final Response response = Response.builder().request(request).status(404).build();
        final FeignException feignException = FeignException.errorStatus("GET", response);
        doThrow(feignException).when(messageStoreClient).ping();

        messageStoreVerifier.verify();

        final ArgumentCaptor<AvailabilityChangeEvent> eventArgumentCaptor = ArgumentCaptor.forClass(AvailabilityChangeEvent.class);
        verify(eventPublisher).publishEvent(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue()).isEqualToIgnoringGivenFields(new AvailabilityChangeEvent<>(feignException, ReadinessState.REFUSING_TRAFFIC), "timestamp");
    }
}