package com.fsabino.fitness.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fsabino.fitness.dto.ClientSubscriptionCodeDTO;
import com.fsabino.fitness.request.AddClientSubscriptionRequest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddSubscriptionDefinition {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AcceptanceState acceptanceState;
	    
    @When("^I want to add a subscription$")
    public void i_want_to_add_a_subscription() throws Throwable {
        AddClientSubscriptionRequest addClientSubscriptionRequest = new AddClientSubscriptionRequest();
        addClientSubscriptionRequest.setProductCode(acceptanceState.getProductCode());
        addClientSubscriptionRequest.setSubscriptionCode(acceptanceState.getSubscriptionCode());
        
        ResponseEntity<ClientSubscriptionCodeDTO> response = restTemplate.postForEntity("/subscription/v1", addClientSubscriptionRequest, 
        		ClientSubscriptionCodeDTO.class);
        assertThat(response).isNotNull();
        acceptanceState.setClientSubscriptionCodeResponse(response);
    }

    @Then("^The subscription is created$")
    public void the_subscription_is_created() throws Throwable {
        assertThat(acceptanceState.getClientSubscriptionCodeResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @And("^I have \"([^\"]*)\" as subscription code$")
    public void i_have_something_as_subscription_code(String subscriptionCode) throws Throwable {
       acceptanceState.setSubscriptionCode(subscriptionCode);
    }

    @And("^I get the subscription code$")
    public void i_get_the_subscription_code() throws Throwable {
    	assertThat(acceptanceState.getClientSubscriptionCodeResponse().getBody().getCode()).isNotNull();
    }
    
    @Then("^I get the http status \"([^\"]*)\"$")
    public void i_get_the_http_status_something(String httpStatus) throws Throwable {
    	assertThat(acceptanceState.getClientSubscriptionCodeResponse().getStatusCode())
    		.isEqualTo(HttpStatus.valueOf(httpStatus));
    }

    @And("^The message is not empty$")
    public void the_message_is_not_empty() throws Throwable {
    	assertThat(acceptanceState.getClientSubscriptionCodeResponse().getBody().getMessage()).isNotBlank();
    }
}
