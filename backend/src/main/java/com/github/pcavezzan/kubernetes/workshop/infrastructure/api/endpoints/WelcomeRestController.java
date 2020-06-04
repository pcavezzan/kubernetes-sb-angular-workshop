package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.endpoints;

import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.resources.MessageResource;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.services.MessageResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
@Slf4j
class WelcomeRestController {

    private final MessageResourceService messageResourceService;
    private final String serverHostName;

    @Autowired
    public WelcomeRestController(MessageResourceService messageResourceService, String serverHostName) {
        this.messageResourceService = messageResourceService;
        this.serverHostName = serverHostName;
    }

    @GetMapping
    public MessageResource get() {
        log.info("{} - incoming request for getting message", serverHostName);
        return messageResourceService.getWelcome();
    }
}
