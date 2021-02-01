package com.fsabino.fitness.repository.client;

import java.util.Optional;

import com.fsabino.fitness.persistence.Client;

public interface ClientRepository {

	Optional<Client> getClientByIdentification(String identification);
}
