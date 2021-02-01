package com.fsabino.fitness.repository.subscription;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.fsabino.fitness.persistence.ClientSubscriptionStatus;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus.Status;
import com.google.common.collect.Lists;

@Repository
public class ClientSubscriptionStatusRepositoryMockImpl implements ClientSubscriptionStatusRepository {

	private final static List<ClientSubscriptionStatus> CLIENTS_SUBSCRIPTIONS_STATUS = Lists.newArrayList();
	
	@Override
	public void addClientSubscriptionStatus(ClientSubscriptionStatus clientSubscriptionStatus) {
		CLIENTS_SUBSCRIPTIONS_STATUS.add(setIdClientSubscriptionStatus(clientSubscriptionStatus));
	}

	@Override
	public Optional<ClientSubscriptionStatus> getCurrentClientSubscriptionStatus(long clientSubscriptionId) {
		return CLIENTS_SUBSCRIPTIONS_STATUS.stream()
			.filter(clientSubscriptionStatus -> clientSubscriptionStatus.getClientSubscription()
					.getId().equals(clientSubscriptionId))
			.filter(clientSubscriptionStatus -> clientSubscriptionStatus.getStartDate().isBeforeNow())
			.filter(clientSubscriptionStatus -> Objects.isNull(clientSubscriptionStatus.getEndDate()))
			.findFirst();
	}
	
	@Override
	public void updateEndDateClientSubscriptionStatus(long clientSubscriptionStatusId) {
		CLIENTS_SUBSCRIPTIONS_STATUS.stream()
			.filter(clientSubscriptionStatus -> clientSubscriptionStatus.getId().equals(clientSubscriptionStatusId))
			.findFirst()
			.ifPresent(clientSubscriptionStatus -> {
				clientSubscriptionStatus.setEndDate(DateTime.now());
			});
	}
	
	@Override
	public boolean isActiveClientSubscription(long clientSubscriptionId) {
		return CLIENTS_SUBSCRIPTIONS_STATUS.stream()
				.filter(clientSubscriptionStatus -> clientSubscriptionStatus.getClientSubscription()
						.getId().equals(clientSubscriptionId))
				.filter(clientSubscriptionStatus -> clientSubscriptionStatus.getStartDate().isBeforeNow())
				.filter(clientSubscriptionStatus -> Objects.isNull(clientSubscriptionStatus.getEndDate()))
				.filter(clientSubscriptionStatus -> clientSubscriptionStatus.getStatus() == Status.ACTIVE)
				.findFirst()
				.isPresent();
	}
	
	private ClientSubscriptionStatus setIdClientSubscriptionStatus(ClientSubscriptionStatus clientSubscriptionStatus) {
		return ClientSubscriptionStatus.builder()
			.status(clientSubscriptionStatus.getStatus())
			.startDate(clientSubscriptionStatus.getStartDate())
			.endDate(clientSubscriptionStatus.getEndDate())
			.clientSubscription(clientSubscriptionStatus.getClientSubscription())
			.id(RandomUtils.nextLong())
			.build();
	}
}
