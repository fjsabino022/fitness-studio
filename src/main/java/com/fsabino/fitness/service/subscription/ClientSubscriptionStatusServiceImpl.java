package com.fsabino.fitness.service.subscription;

import java.util.Optional;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fsabino.fitness.persistence.ClientSubscription;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus.Status;
import com.fsabino.fitness.repository.subscription.ClientSubscriptionStatusRepository;

@Service
public class ClientSubscriptionStatusServiceImpl implements ClientSubscriptionStatusService {

	private final Logger logger = LoggerFactory.getLogger(ClientSubscriptionStatusServiceImpl.class);
	
	private final ClientSubscriptionStatusRepository clientSubscriptionStatusRepository;
	
	@Inject
	public ClientSubscriptionStatusServiceImpl(ClientSubscriptionStatusRepository clientSubscriptionStatusRepository) {
		this.clientSubscriptionStatusRepository = clientSubscriptionStatusRepository;
	}
	
	@Override
	public void addClientSubscriptionStatus(ClientSubscription clientSubscription, Status status) {
		clientSubscriptionStatusRepository.getCurrentClientSubscriptionStatus(clientSubscription.getId())
			.ifPresent(clientSubscriptionStatus -> {
				logger.info("Updated clientSubscriptionStatusId={}", clientSubscriptionStatus.getId());
				clientSubscriptionStatusRepository.updateEndDateClientSubscriptionStatus(clientSubscriptionStatus.getId());
			});
		
		ClientSubscriptionStatus clientSubscriptionStatus = ClientSubscriptionStatus.builder()
				.status(status)
				.startDate(DateTime.now())
				.endDate(null)
				.clientSubscription(clientSubscription)
				.build();
		logger.info("addClientSubscriptionStatus - Add ClientSubscriptionStatus status={}", 
				clientSubscriptionStatus.getStatus());
		clientSubscriptionStatusRepository.addClientSubscriptionStatus(clientSubscriptionStatus);
	}

	@Override
	public Optional<ClientSubscriptionStatus> getCurrentClientSubscriptionStatus(long clientSubscriptionId) {
		return clientSubscriptionStatusRepository.getCurrentClientSubscriptionStatus(clientSubscriptionId);
	}

	@Override
	public boolean isActiveClientSubscription(long clientSubscriptionId) {
		return clientSubscriptionStatusRepository.isActiveClientSubscription(clientSubscriptionId);
	}
}
