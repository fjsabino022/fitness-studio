package com.fsabino.fitness.repository.subscription;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.fsabino.fitness.persistence.ClientSubscription;
import com.google.common.collect.Lists;

@Repository
public class ClientSubscriptionRepositoryMockImpl implements ClientSubscriptionRepository {

	private final static List<ClientSubscription> CLIENTS_SUBSCRIPTIONS = Lists.newArrayList();
	
	@Override
	public ClientSubscription addClientSubscription(ClientSubscription clientSubscription) {
		ClientSubscription newClientSubscription = setIdClientSubscription(clientSubscription);
		CLIENTS_SUBSCRIPTIONS.add(newClientSubscription);	
		return newClientSubscription;
	}
	
	@Override
	public List<ClientSubscription> getClientSubscriptions() {
		return CLIENTS_SUBSCRIPTIONS;
	}
	
	@Override
	public Optional<ClientSubscription> getClientSubscriptionsByCode(String clientSubscriptionCode) {
		return CLIENTS_SUBSCRIPTIONS.stream()
				.filter(clientSubscription -> StringUtils.equals(clientSubscription.getCode(), clientSubscriptionCode))
				.findFirst();
	}
	
	private ClientSubscription setIdClientSubscription(ClientSubscription clientSubscription) {
		return ClientSubscription.builder()
				.id(RandomUtils.nextLong())
				.code(clientSubscription.getCode())
				.client(clientSubscription.getClient())
				.startDate(clientSubscription.getStartDate())
				.endDate(clientSubscription.getEndDate())
				.subscription(clientSubscription.getSubscription())
				.build();
	}
}
