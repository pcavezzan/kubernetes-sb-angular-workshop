package com.github.pcavezzan.kubernetes.workshop.infrastructure.kubernetes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageStoreVerifierScheduledTaskTestCase {

    @Mock
    private MessageStoreVerifier messageStoreVerifier;

    @InjectMocks
    private MessageStoreVerifierScheduledTask messageStoreVerifierScheduledTask;

    @Test
    void verifyMessageStoreState() {
        messageStoreVerifierScheduledTask.verifyMessageStoreState();

        verify(messageStoreVerifier).verify();
    }
}