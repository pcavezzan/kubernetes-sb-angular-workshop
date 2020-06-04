package com.github.pcavezzan.kubernetes.workshop.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class MessageService {

    private static final String WELCOME_MSG_ID = "welcome";
    private static final String SERVER_HOST_NAME_MSG_ID = "server_host_name";
    private static final String BUILD_INFO_MSG_ID = "build_info";
    private static final String INFO_MSG_ID = "info";
    private final MessageRepository messageRepository;

    public Message getWelcomeMsg() {
        return findById(WELCOME_MSG_ID);
    }

    public Message getServerHostName() {
        return findById(SERVER_HOST_NAME_MSG_ID);
    }

    public Message getBuildInfo() {
        return findById(BUILD_INFO_MSG_ID);
    }

    public Message getInfo() {
        return findById(INFO_MSG_ID);
    }

    public String save(Message message) {
        return messageRepository.save(message);
    }

    Message findById(String msgId) {
        final Message message = messageRepository.findById(msgId);
        if (message == null) {
            log.info("Message with key={} does not exist", msgId);
            return new Message(msgId, "");
        }
        return message;
    }
}
