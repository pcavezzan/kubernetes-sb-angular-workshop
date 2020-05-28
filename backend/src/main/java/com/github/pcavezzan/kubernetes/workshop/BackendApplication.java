package com.github.pcavezzan.kubernetes.workshop;

import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import com.github.pcavezzan.kubernetes.workshop.domain.MessageRepository;
import com.github.pcavezzan.kubernetes.workshop.domain.MessageService;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.services.MessageResourceService;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories.MessageRepositoryFeignImpl;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories.MessageStoreClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(BackendApplication.BackendApplicationProperties.class)
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    private static final String BUILD_INFO_FORMAT = "v%s";
    private static final String INFO_MSG_FORMAT = "%s running on %s";

    @Data
    @ConfigurationProperties(prefix = "backend")
    static class BackendApplicationProperties {
        private String version;
        private Messages messages = new Messages();
        private String storeUrl;

        @Data
        static class Messages {
            private String welcome;
        }
    }

    @Autowired
    private BackendApplicationProperties backendApplicationProperties;

    @Autowired
    private Optional<BuildProperties> buildProperties;

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
    public MessageRepositoryFeignImpl messageRepositoryFeign(MessageStoreClient messageStoreClient) {
        return new MessageRepositoryFeignImpl(messageStoreClient);
    }

    @Bean
    public MessageService messageService(MessageRepository messageRepository) {
        return new MessageService(messageRepository);
    }

    @Bean
    public MessageResourceService messageResourceService(MessageService messageService) {
        return new MessageResourceService(messageService);
    }

    @Bean
    public CommandLineRunner boostrapMessages(MessageRepository messageRepository, String serverHostName) {
        return args -> {
            String version = backendApplicationProperties.getVersion();
            if (buildProperties.isPresent()) {
                final BuildProperties properties = buildProperties.get();
                version = properties.getVersion();
            }

            final String buildInfoMsg = String.format(BUILD_INFO_FORMAT, version);
            final String infoMsg = String.format(INFO_MSG_FORMAT, buildInfoMsg, serverHostName);
            final String welcomeMsg = backendApplicationProperties.getMessages().getWelcome();
            messageRepository.save(new Message("welcome", welcomeMsg));
            messageRepository.save(new Message("server_host_name", serverHostName));
            messageRepository.save(new Message("build_info", buildInfoMsg));
            messageRepository.save(new Message("info", infoMsg));
        };
    }

}

