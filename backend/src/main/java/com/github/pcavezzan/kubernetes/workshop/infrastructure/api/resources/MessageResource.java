package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.resources;

import lombok.Data;

@Data
public class MessageResource {
    private final String payload;
}
