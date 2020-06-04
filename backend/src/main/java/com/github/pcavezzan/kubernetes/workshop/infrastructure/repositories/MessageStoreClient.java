package com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stores", url = "${backend.messages.store.url}")
public interface MessageStoreClient {

    @GetMapping("/ping")
    void ping();

    @GetMapping("/messages/{key}")
    RemoteMessageStore get(@PathVariable("key") String key);

    @PutMapping(value = "/messages/{key}", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void put(@PathVariable("key") String key, @RequestBody RemoteMessageStore value);
}
