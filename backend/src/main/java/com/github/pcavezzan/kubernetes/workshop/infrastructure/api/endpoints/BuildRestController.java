package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.endpoints;

import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.resources.MessageResource;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.services.MessageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/build")
public class BuildRestController {

    private final MessageResourceService messageResourceService;

    @Autowired
    public BuildRestController(MessageResourceService messageResourceService) {
        this.messageResourceService = messageResourceService;
    }

    @GetMapping
    public MessageResource get() {
        return messageResourceService.getBuildInfo();
    }
}
