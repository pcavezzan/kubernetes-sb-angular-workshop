package com.github.pcavezzan.kubernetes.workshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whoami")
@Slf4j
public class WhoamiResource {

    private final MessageService messageService;

    @Autowired
    public WhoamiResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public Message get() {
        return messageService.getServerHostName();
    }
}
