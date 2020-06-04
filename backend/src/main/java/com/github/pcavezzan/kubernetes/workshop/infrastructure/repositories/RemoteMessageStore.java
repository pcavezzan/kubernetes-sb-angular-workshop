package com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteMessageStore {
    private String key;
    private String value;
}
