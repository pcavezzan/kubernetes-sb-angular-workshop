package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.services;

import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import com.github.pcavezzan.kubernetes.workshop.domain.MessageService;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.resources.MessageResource;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageResourceService {

    private final MessageService messageService;

    public MessageResource getBuildInfo() {
        final Message info = messageService.getBuildInfo();
        return new MessageResource(info.value());
    }

    public MessageResource getInfo() {
        final Message info = messageService.getInfo();
        return new MessageResource(info.value());
    }

    public MessageResource getWelcome() {
        final Message welcome = messageService.getWelcomeMsg();
        return new MessageResource(welcome.value());
    }

    public MessageResource getWhoami() {
        final Message whoami = messageService.getServerHostName();
        return new MessageResource(whoami.value());
    }
}
