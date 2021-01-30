package com.fsabino.fitness.service.product;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fsabino.fitness.dto.ProductDTO;
import com.fsabino.fitness.exception.ProductNotFoundException;
import com.fsabino.fitness.helper.ProductHelper;
import com.fsabino.fitness.persistence.Product;
import com.fsabino.fitness.repository.ProductRepository;
import com.google.common.collect.Lists;

@Service
public class ProductServiceImpl implements ProductService {

	private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	private final ProductRepository productRepository;
	private final ProductHelper productHelper;
	
	@Inject	
	public ProductServiceImpl(ProductRepository productRepository, ProductHelper productHelper) {
		this.productRepository = productRepository;
		this.productHelper = productHelper;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.getAllProducts();
		
		if (CollectionUtils.isEmpty(products)) {
			return Lists.newArrayList();
		}
		return products.stream()
				.map(productHelper::modelToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public ProductDTO getProductByCode(String code) throws ProductNotFoundException {
		Product product = productRepository.getProductByCode(code)
				.orElseThrow(() -> {
					logger.error("getProductByCode - Product with id={} could not be found", code);
					return new ProductNotFoundException(String.format("Product with id=%s could not be found", code));
				});
		return productHelper.modelToDTO(product);
	}
}
