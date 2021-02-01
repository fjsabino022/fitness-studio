package com.fsabino.fitness.helper;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.fsabino.fitness.dto.ProductDTO;
import com.fsabino.fitness.dto.SubscriptionDTO;
import com.fsabino.fitness.persistence.Product;
import com.fsabino.fitness.persistence.Subscription;
import com.google.common.collect.Lists;

@Component
public class ProductHelper {

	private final SubscriptionHelper subscriptionHelper;
		
	@Inject
	public ProductHelper(SubscriptionHelper subscriptionHelper) {
		this.subscriptionHelper = subscriptionHelper;
	}

	public ProductDTO modelToDTO(Product product) {
		List<SubscriptionDTO> subscriptionDTOs = getSubscriptions(product.getSubscriptions());
		
		ProductDTO productDTO = ProductDTO.builder()
				.code(product.getCode())
				.description(product.getDescription())
				.name(product.getName())
				.subscriptions(subscriptionDTOs)
				.build();
		return productDTO;
	}
	
	public ProductDTO modelToDTOWithoutSubscripcions(Product product) {
		ProductDTO productDTO = ProductDTO.builder()
				.code(product.getCode())
				.description(product.getDescription())
				.name(product.getName())
				.build();
		return productDTO;
	}
	
	private List<SubscriptionDTO> getSubscriptions(List<Subscription> subscriptions) {
		if (CollectionUtils.isEmpty(subscriptions)) {
			return Lists.newArrayList();
		}
		return subscriptions.stream()
				.map(subscriptionHelper::modelToDTO)
				.collect(Collectors.toList());
	}
}
