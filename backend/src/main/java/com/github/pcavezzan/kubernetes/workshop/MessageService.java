package com.github.pcavezzan.kubernetes.workshop;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class MessageService {

	private final String welcomeMsg;
	private final String serverHostName;

	public Message getWelcomeMsg() {
		return new Message(welcomeMsg);
	}

	public Message getServerHostName() {
		return new Message(serverHostName);
	}

}
