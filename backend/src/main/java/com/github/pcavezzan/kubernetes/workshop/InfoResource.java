package com.github.pcavezzan.kubernetes.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/info")
@RestController
public class InfoResource {

    private final MessageService messageService;

    @Autowired
    public InfoResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public Message get() {
        return messageService.getInfo();
    }
}
