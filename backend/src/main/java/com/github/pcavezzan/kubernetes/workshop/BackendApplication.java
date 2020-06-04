package com.github.pcavezzan.kubernetes.workshop;

import com.github.pcavezzan.kubernetes.workshop.domain.Message;
import com.github.pcavezzan.kubernetes.workshop.domain.MessageRepository;
import com.github.pcavezzan.kubernetes.workshop.domain.MessageService;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.api.services.MessageResourceService;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.kubernetes.MessageStoreVerifier;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.kubernetes.MessageStoreVerifierScheduledTask;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories.MessageRepositoryFeignImpl;
import com.github.pcavezzan.kubernetes.workshop.infrastructure.repositories.MessageStoreClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableConfigurationProperties(BackendApplication.BackendApplicationProperties.class)
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    private static final String BUILD_INFO_FORMAT = "v%s";
    private static final String INFO_MSG_FORMAT = "%s running on %s";
    @Autowired
    private BackendApplicationProperties backendApplicationProperties;
    @Autowired
    private Optional<BuildProperties> buildProperties;

    @Bean
    public MessageRepositoryFeignImpl messageRepositoryFeign(MessageStoreClient messageStoreClient) {
        return new MessageRepositoryFeignImpl(messageStoreClient);
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
    public MessageService messageService(MessageRepository messageRepository) {
        return new MessageService(messageRepository);
    }

    @Bean
    public MessageResourceService messageResourceService(MessageService messageService) {
        return new MessageResourceService(messageService);
    }

    @Bean
    @ConditionalOnProperty(name = "backend.messages.store.bootstrap", havingValue = "true")
    public CommandLineRunner boostrapMessages(MessageStoreVerifier messageStoreVerifier,
                                              MessageRepository messageRepository,
                                              String serverHostName) {
        return args -> {
            while (!messageStoreVerifier.verify()) {
                log.warn("Remote storage is unavailable, waiting for 2secs until next check in order to setup initial messages.");
                Thread.sleep(2000);
            }

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

    @Bean
    public MessageStoreVerifier messageStoreVerifier(
            MessageStoreClient messageStoreClient,
            ApplicationEventPublisher eventPublisher
    ) {
        return new MessageStoreVerifier(messageStoreClient, eventPublisher);
    }

    @Bean
    public MessageStoreVerifierScheduledTask messageStoreVerifierScheduledTask(MessageStoreVerifier messageStoreVerifier) {
        return new MessageStoreVerifierScheduledTask(messageStoreVerifier);
    }
}

