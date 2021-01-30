package com.fsabino.fitness.repository;

import java.util.List;
import java.util.Optional;

import com.fsabino.fitness.persistence.Product;

public interface ProductRepository {

	List<Product> getAllProducts();
	
	Optional<Product> getProductByCode(String code);
}
