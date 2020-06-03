package com.github.pcavezzan.kubernetes.workshop.infrastructure.kubernetes;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
@Slf4j
public class MessageStoreVerifierScheduledTask {
    private final MessageStoreVerifier messageStoreVerifier;

    @Scheduled(fixedRate = 10000)
    public void verifyMessageStoreState() {
        messageStoreVerifier.verify();
    }
}
