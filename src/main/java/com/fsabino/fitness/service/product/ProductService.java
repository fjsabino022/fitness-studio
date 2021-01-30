package com.fsabino.fitness.service.product;

import java.util.List;

import com.fsabino.fitness.dto.ProductDTO;
import com.fsabino.fitness.exception.ProductNotFoundException;

public interface ProductService {

	List<ProductDTO> getAllProducts();
	
	ProductDTO getProductByCode(String code) throws ProductNotFoundException;
}
