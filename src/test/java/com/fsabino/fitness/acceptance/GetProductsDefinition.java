package com.fsabino.fitness.acceptance;

import com.fsabino.fitness.dto.ProductDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class GetProductsDefinition {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AcceptanceState acceptanceState;

    @When("^I want to get all products$")
    public void i_want_to_get_all_products() throws Throwable {
        ResponseEntity<ProductDTO[]> result = restTemplate.getForEntity("/product/v1", ProductDTO[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotEmpty();
        acceptanceState.setProducts(List.of(result.getBody()));
    }

    @Then("^The list of products is returned$")
    public void the_list_of_products_is_returned() throws Throwable {
    	assertThat(acceptanceState.getProducts()).isNotEmpty();
    }

    @And("^The number of product is \"([^\"]*)\"$")
    public void the_number_of_product_is_something(String numberOfProducts) throws Throwable {
    	assertThat(acceptanceState.getProducts()).hasSize(Integer.parseInt(numberOfProducts));
    }

    @And("^All products have at least one subscription$")
    public void all_products_have_at_least_one_subscription() throws Throwable {
    	assertThat(acceptanceState.getProducts().stream()
    		.allMatch(product -> CollectionUtils.isNotEmpty(product.getSubscriptions()))).isTrue();
    }
}
