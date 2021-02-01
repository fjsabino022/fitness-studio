package com.fsabino.fitness.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fsabino.fitness.dto.ClientSubscriptionDTO;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus.Status;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetMySubscriptionsDefinition {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AcceptanceState acceptanceState;
	
    @When("^I want to get my subscriptions$")
    public void i_want_to_get_my_subscriptions() throws Throwable {
    	ResponseEntity<ClientSubscriptionDTO[]> result = 
    			restTemplate.getForEntity("/subscription/v1", ClientSubscriptionDTO[].class);
    	assertThat(result).isNotNull();
    	assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    	acceptanceState.setClientSubscriptionsResponse(result);
    }

    @Then("^I get my subscriptions$")
    public void i_get_my_subscriptions() throws Throwable {
    	assertThat(acceptanceState.getClientSubscriptionsResponse().getBody()).hasSize(2);
    }

    @And("^They are active subscriptions$")
    public void they_are_active_subscriptions() throws Throwable {
    	assertThat(Arrays.stream(acceptanceState.getClientSubscriptionsResponse().getBody())
    			.allMatch(clientSubscription -> 
    				Status.valueOf(clientSubscription.getClientSubscriptionStatus().getStatus()) == Status.ACTIVE))
    			.isTrue();
    }

    @And("^They are mine$")
    public void they_are_mine() throws Throwable {
    	assertThat(Arrays.stream(acceptanceState.getClientSubscriptionsResponse().getBody())
    			.allMatch(clientSubscription -> 
    				StringUtils.equals("identification", clientSubscription.getClient().getIdentification())))
    			.isTrue();
    }
}
