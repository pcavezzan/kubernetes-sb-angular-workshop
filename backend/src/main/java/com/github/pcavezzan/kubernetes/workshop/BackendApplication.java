package com.github.pcavezzan.kubernetes.workshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class BackendApplication {

	@Value("${backend.messages.welcome}")
	private String welcomeMsg;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public String serverHostName(ServerProperties serverProperties) {
		InetAddress address = serverProperties.getAddress();
		if (address == null) {
			// We did not set any binding address
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				log.warn("Could not get address from localhost, will use loopback instead");
				address = InetAddress.getLoopbackAddress();
			}
		}
		return address.getHostName();
	}

	@Bean
	public MessageService messageService(String serverHostName) {
		return new MessageService(welcomeMsg, serverHostName);
	}
}

