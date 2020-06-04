package com.github.pcavezzan.kubernetes.workshop;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@SpringBootTest
@AutoConfigureWireMock(port = 19090)
public abstract class AbstractITCase {

    @BeforeAll
    static void setUp() {
        stubFor(get(urlEqualTo("/messages/welcome")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json").withBody("{\"key\":\"welcome\",\"value\":\"** Hello World **\"}")));
        stubFor(get(urlEqualTo("/messages/server_host_name")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json").withBody("{\"key\":\"server_host_name\",\"value\":\"localhost\"}")));
        stubFor(get(urlEqualTo("/messages/build_info")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json").withBody("{\"key\":\"build_info\",\"value\":\"vTest\"}")));
        stubFor(get(urlEqualTo("/messages/info")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json").withBody("{\"key\":\"info\",\"value\":\"vTest running on localhost\"}")));
        stubFor(put(urlEqualTo("/messages/key")).willReturn(noContent()));
    }

}
