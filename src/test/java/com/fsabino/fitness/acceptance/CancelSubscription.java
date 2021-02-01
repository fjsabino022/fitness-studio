package com.fsabino.fitness.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.cucumber.java.en.And;

public class CancelSubscription {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AcceptanceState acceptanceState;
	
    @And("^I cancel my subscription$")
    public void i_cancel_my_subscription() throws Throwable {
    	String url = UriComponentsBuilder.fromPath("/subscription/v1/")
    			.path(String.valueOf(acceptanceState.getClientSubscriptionCodeResponse().getBody().getCode()))
    			.build()
    			.toUriString();
    	
    	restTemplate.delete(url);
    }
}
