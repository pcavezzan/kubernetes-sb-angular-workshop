package com.github.pcavezzan.kubernetes.workshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
@Slf4j
class WelcomeResource {

    private final MessageService messageService;
    private final String serverHostName;

    @Autowired
    public WelcomeResource(MessageService messageService, String serverHostName) {
        this.messageService = messageService;
        this.serverHostName = serverHostName;
    }

    @GetMapping
    public Message get() {
        log.info("{} - incoming request for getting message", serverHostName);
        return messageService.getWelcomeMsg();
    }
}
