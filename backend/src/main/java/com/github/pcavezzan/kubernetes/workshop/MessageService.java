package com.github.pcavezzan.kubernetes.workshop;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class MessageService {

	private final String welcomeMsg;
	private final String serverHostName;
	private final String buildInfo;
	private final String info;
	public Message getWelcomeMsg() {
		return new Message(welcomeMsg);
	}

	public Message getServerHostName() {
		return new Message(serverHostName);
	}

	public Message getBuildInfo() {
		return new Message(buildInfo);
	}

	public Message getInfo() {
		return new Message(info);
	}
}
