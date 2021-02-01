package com.fsabino.fitness.service.subscription;

import java.util.Optional;

import com.fsabino.fitness.persistence.Subscription;

public interface SubscriptionService {

	Optional<Subscription> getSubscriptionByCodeAndProductCode(String subscriptionCode, String productCode);
}
