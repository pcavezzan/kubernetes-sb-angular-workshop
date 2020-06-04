package com.github.pcavezzan.kubernetes.workshop.infrastructure.api.endpoints;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

// FIX: @WebMvcTest fails with @EnableFeignClients
// https://github.com/spring-projects/spring-boot/issues/7270
@ImportAutoConfiguration({FeignAutoConfiguration.class})
abstract class AbstractRestControllerTestCase {
}
