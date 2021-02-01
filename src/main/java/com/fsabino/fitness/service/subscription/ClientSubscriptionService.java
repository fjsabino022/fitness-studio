package com.fsabino.fitness.service.subscription;

import java.util.List;

import com.fsabino.fitness.dto.ClientSubscriptionCodeDTO;
import com.fsabino.fitness.dto.ClientSubscriptionDTO;
import com.fsabino.fitness.exception.ClientSubscriptionNotFoundException;
import com.fsabino.fitness.exception.IlegalProductCodeException;
import com.fsabino.fitness.exception.IlegalSubscriptionCodeException;
import com.fsabino.fitness.exception.SubscriptionNotFoundException;
import com.fsabino.fitness.request.AddClientSubscriptionRequest;
import com.fsabino.fitness.request.UpdateStatusClientSubscriptionRequest;

public interface ClientSubscriptionService {

	ClientSubscriptionCodeDTO addClientSubscription(AddClientSubscriptionRequest addClientSubscriptionRequest) 
			throws IlegalProductCodeException, IlegalSubscriptionCodeException, SubscriptionNotFoundException;
	
	List<ClientSubscriptionDTO> getActiveClientSubscriptions();
	
	ClientSubscriptionCodeDTO updateStatusClientSubscription(String clientSubscriptionCode, 
			UpdateStatusClientSubscriptionRequest updateStatusClientSubscriptionRequest) throws ClientSubscriptionNotFoundException;
	
	ClientSubscriptionCodeDTO cancelClientSubscription(String clientSubscriptionCode) throws ClientSubscriptionNotFoundException;
}