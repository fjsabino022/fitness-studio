package com.fsabino.fitness.service.subscription;

import java.util.Optional;

import com.fsabino.fitness.persistence.ClientSubscription;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus.Status;

public interface ClientSubscriptionStatusService {

	void addClientSubscriptionStatus(ClientSubscription clientSubscription, Status status);
	
	Optional<ClientSubscriptionStatus> getCurrentClientSubscriptionStatus(long clientSubscriptionId);
	
	boolean isActiveClientSubscription(long clientSubscriptionId);
}
