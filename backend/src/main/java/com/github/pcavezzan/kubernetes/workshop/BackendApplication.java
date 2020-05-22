package com.github.pcavezzan.kubernetes.workshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class BackendApplication {

    private static final String BUILD_INFO_FORMAT = "v%s";

    private static final String INFO_MSG_FORMAT = "%s running on %s";


    @Value("${backend.messages.welcome}")
    private String welcomeMsg;

    @Value("${backend.version}")
    private String defaultVersion;

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
    public String buildInfo(Optional<BuildProperties> buildProperties) {
        String version = defaultVersion;
        if (buildProperties.isPresent()) {
            final BuildProperties properties = buildProperties.get();
            version = properties.getVersion();
        }
        return String.format(BUILD_INFO_FORMAT, version);
    }


    @Bean
    public MessageService messageService(
            String serverHostName,
            String buildInfo
    ) {
        final String info = String.format(INFO_MSG_FORMAT, buildInfo, serverHostName);
        return new MessageService(welcomeMsg, serverHostName, buildInfo, info);
    }
}

