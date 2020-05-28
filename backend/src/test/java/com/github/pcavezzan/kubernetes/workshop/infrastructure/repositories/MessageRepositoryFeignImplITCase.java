package com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories;


import com.github.pcavezzan.kubernetes.workshop.AbstractITCase;
import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageRepositoryFeignImplITCase extends AbstractITCase {

    @Autowired
    private MessageRepositoryFeignImpl messageRepositoryFeign;

    @Test
    void findByIdShouldReturnMessage() {
        final Message message = messageRepositoryFeign.findById("welcome");

        assertThat(message).isEqualTo(new Message("welcome", "** Hello World **"));
    }

    @Test
    void findByIdShouldReturnNull() {
        final Message message = messageRepositoryFeign.findById("doesNotExists");

        assertThat(message).isNull();
    }

    @Test
    void saveShouldReturnKey() {
        final Message message = new Message("key", "value");

        final String result = messageRepositoryFeign.save(message);

        assertThat(result).isEqualTo("key");
    }

    @Test
    void saveShouldReturnNull() {
        final Message message = new Message("willFaild", "Failing value");

        final String result = messageRepositoryFeign.save(message);

        assertThat(result).isNull();
    }
}
