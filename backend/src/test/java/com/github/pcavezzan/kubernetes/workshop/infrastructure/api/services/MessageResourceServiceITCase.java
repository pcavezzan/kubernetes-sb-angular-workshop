package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.services;

import com.github.pcavezzan.kubernetes.workshop.AbstractITCase;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.resources.MessageResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageResourceServiceITCase extends AbstractITCase {

    @Autowired
    private MessageResourceService messageResourceService;

    @Test
    void getBuildInfo() {
        final MessageResource result = messageResourceService.getBuildInfo();

        assertThat(result).isEqualTo(new MessageResource("vTest"));
    }

    @Test
    void getInfo() {
        final MessageResource result = messageResourceService.getInfo();

        assertThat(result).isEqualTo(new MessageResource("vTest running on localhost"));
    }

    @Test
    void getWelcome() {
        final MessageResource result = messageResourceService.getWelcome();

        assertThat(result).isEqualTo(new MessageResource("** Hello World **"));
    }

    @Test
    void getWhoami() {
        final MessageResource result = messageResourceService.getWhoami();

        assertThat(result).isEqualTo(new MessageResource("localhost"));
    }
}
