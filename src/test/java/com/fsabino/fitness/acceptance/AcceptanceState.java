package com.fsabino.fitness.acceptance;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fsabino.fitness.dto.ProductDTO;

@Component
public class AcceptanceState {

    private List<ProductDTO> products;
    private String productCode;
    private ResponseEntity<ProductDTO> productResponse;

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
}
