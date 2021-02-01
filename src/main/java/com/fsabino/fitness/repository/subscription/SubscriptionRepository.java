package com.fsabino.fitness.repository.subscription;

import java.util.Optional;

import com.fsabino.fitness.persistence.Subscription;

public interface SubscriptionRepository {

	Optional<Subscription> getSubscriptionByCodeAndProductCode(String subscriptionCode, String productCode);
}
