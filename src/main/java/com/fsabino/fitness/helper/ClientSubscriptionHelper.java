package com.fsabino.fitness.helper;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.fsabino.fitness.dto.ClientSubscriptionDTO;
import com.fsabino.fitness.persistence.ClientSubscription;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus;

@Component
public class ClientSubscriptionHelper {

	private final ClientHelper clientHelper;
	private final ProductHelper productHelper;
	private final SubscriptionHelper subscriptionHelper;
	private final ClientSubscriptionStatusHelper clientSubscriptionStatusHelper;
	
	@Inject
	public ClientSubscriptionHelper(ClientHelper clientHelper, ProductHelper productHelper,
			SubscriptionHelper subscriptionHelper, ClientSubscriptionStatusHelper clientSubscriptionStatusHelper) {
		this.clientHelper = clientHelper;
		this.productHelper = productHelper;
		this.subscriptionHelper = subscriptionHelper;
		this.clientSubscriptionStatusHelper = clientSubscriptionStatusHelper;
	}

	public ClientSubscriptionDTO modelToDTO(ClientSubscription clientSubscription, ClientSubscriptionStatus clientSubscriptionStatus) {
		return ClientSubscriptionDTO.builder()
				.startDate(clientSubscription.getStartDate())
				.endDate(clientSubscription.getEndDate())
				.code(clientSubscription.getCode())
				.product(productHelper.modelToDTO(clientSubscription.getSubscription().getProduct()))
				.client(clientHelper.modelToDTO(clientSubscription.getClient()))
				.subscription(subscriptionHelper.modelToDTO(clientSubscription.getSubscription()))
				.clientSubscriptionStatus(clientSubscriptionStatusHelper.modelToDTO(clientSubscriptionStatus))
				.build();
	}
}
