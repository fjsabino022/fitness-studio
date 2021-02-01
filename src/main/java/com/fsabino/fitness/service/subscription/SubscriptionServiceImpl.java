package com.fsabino.fitness.service.subscription;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fsabino.fitness.persistence.Subscription;
import com.fsabino.fitness.repository.subscription.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	
	private final SubscriptionRepository subscriptionRepository;
	
	@Inject
	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	@Override
	public Optional<Subscription> getSubscriptionByCodeAndProductCode(String subscriptionCode, String productCode) {
		return subscriptionRepository.getSubscriptionByCodeAndProductCode(subscriptionCode, productCode);
	}
}
