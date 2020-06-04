package com.github.pcavezzan.kubernetes.workshop.domain;

public interface MessageRepository {

    Message findById(String id);

    String save(Message message);
}
