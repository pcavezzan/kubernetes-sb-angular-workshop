package com.github.pcavezzan.kubernetes.workshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

@RestController
@RequestMapping("/welcome")
@Slf4j
class WelcomeResource {

    @Autowired
    private MessageService messageService;

    @Autowired
    private String serverHostName;

    @GetMapping
    public Message get() {
        log.info("{} - incoming request for getting message", serverHostName);
        return messageService.getWelcomeMsg();
    }
}
