package com.lemonprogis;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.lemonprogis.model.UsState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 0)
class ApplicationIT {
	@LocalServerPort
	private int port;

	@Autowired
	public WireMockServer wireMockServer;

	private static WebTestClient webTestClient;


	@BeforeEach
	void beforeEach() {
		String baseUrl = String.format("http://localhost:%d", port);
		webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();
	}


	@Test
	public void poiEndpointTests() throws IOException {
		// ACT and ASSERT
		webTestClient.get()
				.uri("/find-state?lat=35.456&lng=-92.985")
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBody(UsState.class)
				.consumeWith(r -> {
					UsState actual = Optional.ofNullable(r.getResponseBody()).orElseThrow();
					assertThat(actual).isNotNull();
					assertThat(actual.getName()).isEqualTo("Arkansas");
					assertThat(actual.getAbbr()).isEqualTo("AR");
				});
	}
}
