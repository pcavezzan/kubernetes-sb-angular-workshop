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

    @Autowired
    private MessageService messageService;

    @GetMapping
    public Message get() {
        return messageService.getServerHostName();
    }
}
