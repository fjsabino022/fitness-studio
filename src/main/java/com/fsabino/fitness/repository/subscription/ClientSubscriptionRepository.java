package com.fsabino.fitness.repository.subscription;

import java.util.List;
import java.util.Optional;

import com.fsabino.fitness.persistence.ClientSubscription;

public interface ClientSubscriptionRepository {

	ClientSubscription addClientSubscription(ClientSubscription clientSubscription);
	
	List<ClientSubscription> getClientSubscriptions();
	
	Optional<ClientSubscription> getClientSubscriptionsByCode(String clientSubscriptionCode);
}
