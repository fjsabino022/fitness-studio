package com.fsabino.fitness.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsabino.fitness.dto.ProductDTO;
import com.fsabino.fitness.exception.ProductNotFoundException;
import com.fsabino.fitness.service.product.ProductService;

@RestController
@RequestMapping(value = "/product/v1")
public class ProductController {

	private final ProductService productService;

	@Inject
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllproducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping(value = "/{code}")
	public ResponseEntity<ProductDTO> getProductByCode(@PathVariable("code") String code) {
		try {
			ProductDTO product = productService.getProductByCode(code);
			return ResponseEntity.ok(product);
		} catch (ProductNotFoundException e) {
			ProductDTO product = ProductDTO.builder()
					.message(e.getMessage())
					.buildFailedResponse();
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(product);
		}
	}
}
