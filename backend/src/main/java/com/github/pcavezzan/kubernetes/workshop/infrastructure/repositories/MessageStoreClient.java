package com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories;

import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stores", url = "${backend.messages.store.url}")
public interface MessageStoreClient {

    @GetMapping("/messages/{key}")
    Message get(@PathVariable("key") String key);

    @PutMapping(value = "/messages/{key}")
    void put(@PathVariable("key") String key, Message value);
}
