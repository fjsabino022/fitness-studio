package com.fsabino.fitness.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.fsabino.fitness.dto.ClientSubscriptionCodeDTO;
import com.fsabino.fitness.dto.ClientSubscriptionDTO;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus.Status;
import com.fsabino.fitness.request.UpdateStatusClientSubscriptionRequest;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PauseMySubscription {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AcceptanceState acceptanceState;

	@When("^I want to pause my subscription$")
	public void i_want_to_pause_my_subscription() throws Throwable {
		// @formatter:off
		String url = UriComponentsBuilder.fromPath("/subscription/v1/")
				.path(String.valueOf(acceptanceState.getClientSubscriptionCodeResponse().getBody().getCode()))
				.build()
				.toUriString();
		// @formatter:on

		UpdateStatusClientSubscriptionRequest updateStatusClientSubscriptionRequest =
				new UpdateStatusClientSubscriptionRequest();
		updateStatusClientSubscriptionRequest.setStatus(Status.PAUSED.toString());

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<UpdateStatusClientSubscriptionRequest> request =
				new HttpEntity<>(updateStatusClientSubscriptionRequest, headers);

		ResponseEntity<ClientSubscriptionCodeDTO> responseEntity =
				restTemplate.exchange(url, HttpMethod.PUT, request, ClientSubscriptionCodeDTO.class);
		assertThat(responseEntity).isNotNull();
		acceptanceState.setClientSubscriptionCodeResponse(responseEntity);
	}

	@Then("^My subscription is not active$")
	public void my_subscription_is_not_active() throws Throwable {
		assertThat(acceptanceState.getClientSubscriptionCodeResponse().getStatusCode()).isEqualTo(HttpStatus.OK);

		ResponseEntity<ClientSubscriptionDTO[]> result =
				restTemplate.getForEntity("/subscription/v1", ClientSubscriptionDTO[].class);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(Arrays.stream(result.getBody())
				.filter(clientSubscription -> StringUtils.equals(clientSubscription.getCode(),
						String.valueOf(acceptanceState.getClientSubscriptionCodeResponse().getBody().getCode())))
				.count()).isZero();
	}
}
