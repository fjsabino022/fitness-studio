package com.fsabino.fitness.helper;

import org.springframework.stereotype.Component;

import com.fsabino.fitness.dto.ClientSubscriptionStatusDTO;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus;

@Component
public class ClientSubscriptionStatusHelper {

	public ClientSubscriptionStatusDTO modelToDTO(ClientSubscriptionStatus clientSubscriptionStatus) {
		return ClientSubscriptionStatusDTO.builder()
				.startDate(clientSubscriptionStatus.getStartDate())
				.status(clientSubscriptionStatus.getStatus().toString())
				.build();
	}
}
