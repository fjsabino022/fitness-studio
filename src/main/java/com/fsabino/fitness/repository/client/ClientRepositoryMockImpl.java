package com.fsabino.fitness.repository.client;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.fsabino.fitness.persistence.Client;
import com.google.common.collect.ImmutableList;

@Repository
public class ClientRepositoryMockImpl implements ClientRepository {

	private static final List<Client> CLIENTS = ImmutableList.<Client>builder()
			.add(createClient(1L, "Name", "Last Name", "identification"))
			.add(createClient(2L, "Name2", "Last Name2", "identification2"))
			.build();
	
	@Override
	public Optional<Client> getClientByIdentification(String identification) {
		return CLIENTS.stream()
				.filter(client -> StringUtils.equals(client.getIdentification(), identification))
				.findFirst();
	}

	private static Client createClient(long id, String name, String lastName, String identification) {
		return Client.builder()
				.id(id)
				.firstName(name)
				.lastName(lastName)
				.identification(identification)
				.build();
	}
}
