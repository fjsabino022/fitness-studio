package com.fsabino.fitness.repository.subscription;

import java.util.Optional;

import com.fsabino.fitness.persistence.ClientSubscriptionStatus;

public interface ClientSubscriptionStatusRepository {

	void addClientSubscriptionStatus(ClientSubscriptionStatus clientSubscriptionStatus);
	
	Optional<ClientSubscriptionStatus> getCurrentClientSubscriptionStatus(long clientSubscriptionId);
	
	void updateEndDateClientSubscriptionStatus(long clientSubscriptionStatusId);
	
	boolean isActiveClientSubscription(long clientSubscriptionId);
}
