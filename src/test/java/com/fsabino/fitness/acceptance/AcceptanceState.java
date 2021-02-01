package com.fsabino.fitness.acceptance;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fsabino.fitness.dto.ClientSubscriptionCodeDTO;
import com.fsabino.fitness.dto.ClientSubscriptionDTO;
import com.fsabino.fitness.dto.ProductDTO;

@Component
public class AcceptanceState {

    private List<ProductDTO> products;
    private String productCode;
    private ResponseEntity<ProductDTO> productResponse;
    private String subscriptionCode;
    private ResponseEntity<ClientSubscriptionCodeDTO> clientSubscriptionCodeResponse;
    private ResponseEntity<ClientSubscriptionDTO[]> clientSubscriptionsResponse;

    public ResponseEntity<ProductDTO> getProductResponse() {
		return productResponse;
	}

	public void setProductResponse(ResponseEntity<ProductDTO> product) {
		this.productResponse = product;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public List<ProductDTO> getProducts() {
        return products;
    }
    
    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

	public String getSubscriptionCode() {
		return subscriptionCode;
	}

	public void setSubscriptionCode(String subscriptionCode) {
		this.subscriptionCode = subscriptionCode;
	}

	public ResponseEntity<ClientSubscriptionCodeDTO> getClientSubscriptionCodeResponse() {
		return clientSubscriptionCodeResponse;
	}

	public void setClientSubscriptionCodeResponse(
			ResponseEntity<ClientSubscriptionCodeDTO> clientSubscriptionCodeResponse) {
		this.clientSubscriptionCodeResponse = clientSubscriptionCodeResponse;
	}

	public ResponseEntity<ClientSubscriptionDTO[]> getClientSubscriptionsResponse() {
		return clientSubscriptionsResponse;
	}

	public void setClientSubscriptionsResponse(ResponseEntity<ClientSubscriptionDTO[]> clientSubscriptionsResponse) {
		this.clientSubscriptionsResponse = clientSubscriptionsResponse;
	}
}
