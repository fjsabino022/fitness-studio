package com.fsabino.fitness.helper;

import org.springframework.stereotype.Component;

import com.fsabino.fitness.dto.ClientDTO;
import com.fsabino.fitness.persistence.Client;

@Component
public class ClientHelper {

	public ClientDTO modelToDTO(Client client) {
		return ClientDTO.builder()
				.firstName(client.getFirstName())
				.lastName(client.getLastName())
				.identification(client.getIdentification())
				.build();
	}
}
