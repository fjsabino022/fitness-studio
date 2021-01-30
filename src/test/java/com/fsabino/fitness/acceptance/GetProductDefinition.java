package com.fsabino.fitness.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.fsabino.fitness.dto.ProductDTO;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetProductDefinition {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AcceptanceState acceptanceState;
	    
	@When("^I want to get the product$")
    public void i_want_to_get_the_product() throws Throwable {
		String url = UriComponentsBuilder.fromPath("/product/v1/")
			.path(acceptanceState.getProductCode())
			.build()
			.toUriString();
		ResponseEntity<ProductDTO> result = restTemplate.getForEntity(url, ProductDTO.class);
		acceptanceState.setProductResponse(result);
	}

    @Then("^The product is returned$")
    public void the_product_is_returned() throws Throwable {
    	assertThat(acceptanceState.getProductResponse()).isNotNull();
    	assertThat(acceptanceState.getProductResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
    	assertThat(acceptanceState.getProductResponse().getBody()).isNotNull();
    }

    @And("^I have \"([^\"]*)\" as product code$")
    public void i_have_something_as_product_code(String code) throws Throwable {
    	acceptanceState.setProductCode(code);
    }

    @And("^The name is \"([^\"]*)\"$")
    public void the_name_is_something(String name) throws Throwable {
    	ProductDTO product = acceptanceState.getProductResponse().getBody();
		assertThat(product.getName()).isEqualTo(name);
    }

    @And("^The description is not empty$")
    public void the_description_is_not_empty() throws Throwable {
    	ProductDTO product = acceptanceState.getProductResponse().getBody();
		assertThat(product.getDescription()).isNotBlank();
    }

    @And("^The product has \"([^\"]*)\" subcriptions$")
    public void the_product_has_something_subcriptions(String numbersubscriptions) throws Throwable {
    	ProductDTO product = acceptanceState.getProductResponse().getBody();
		assertThat(product.getSubscriptions()).hasSize(Integer.parseInt(numbersubscriptions));
    }
    
    @Then("^The not found response is returned$")
    public void the_not_found_response_is_returned() throws Throwable {
    	assertThat(acceptanceState.getProductResponse()).isNotNull();
    	assertThat(acceptanceState.getProductResponse().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    	assertThat(acceptanceState.getProductResponse().getBody()).isNotNull();
    }

    @And("^The message is not null$")
    public void the_message_is_not_null() throws Throwable {
    	ProductDTO result = acceptanceState.getProductResponse().getBody();
		assertThat(result.getMessage()).isNotBlank();
    }
}
