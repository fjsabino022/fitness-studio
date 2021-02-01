package com.fsabino.fitness.service.subscription;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fsabino.fitness.dto.ClientSubscriptionCodeDTO;
import com.fsabino.fitness.dto.ClientSubscriptionDTO;
import com.fsabino.fitness.exception.ClientSubscriptionNotFoundException;
import com.fsabino.fitness.exception.IlegalProductCodeException;
import com.fsabino.fitness.exception.IlegalSubscriptionCodeException;
import com.fsabino.fitness.exception.SubscriptionNotFoundException;
import com.fsabino.fitness.helper.ClientSubscriptionHelper;
import com.fsabino.fitness.persistence.Client;
import com.fsabino.fitness.persistence.ClientSubscription;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus.Status;
import com.fsabino.fitness.persistence.Subscription;
import com.fsabino.fitness.persistence.Subscription.Type;
import com.fsabino.fitness.repository.client.ClientRepository;
import com.fsabino.fitness.repository.subscription.ClientSubscriptionRepository;
import com.fsabino.fitness.request.AddClientSubscriptionRequest;
import com.fsabino.fitness.request.UpdateStatusClientSubscriptionRequest;

@Service
public class ClientSubscriptionServiceImpl implements ClientSubscriptionService {

	private final Logger logger = LoggerFactory.getLogger(ClientSubscriptionServiceImpl.class);

	private final SubscriptionService subscriptionService;
	private final ClientRepository clientRepository;
	private final ClientSubscriptionStatusService clientSubscriptionStatusService;
	private final ClientSubscriptionRepository clientSubscriptionRepository;
	private final ClientSubscriptionHelper clientSubscriptionHelper;

	@Inject
	public ClientSubscriptionServiceImpl(SubscriptionService subscriptionService, ClientRepository clientRepository,
			ClientSubscriptionRepository clientSubscriptionRepository,
			ClientSubscriptionStatusService clientSubscriptionStatusService,
			ClientSubscriptionHelper clientSubscriptionHelper) {
		this.subscriptionService = subscriptionService;
		this.clientRepository = clientRepository;
		this.clientSubscriptionRepository = clientSubscriptionRepository;
		this.clientSubscriptionStatusService = clientSubscriptionStatusService;
		this.clientSubscriptionHelper = clientSubscriptionHelper;
	}

	@Override
	public ClientSubscriptionCodeDTO addClientSubscription(AddClientSubscriptionRequest addClientSubscriptionRequest)
			throws IlegalProductCodeException, IlegalSubscriptionCodeException, SubscriptionNotFoundException {
		String productCode = addClientSubscriptionRequest.getProductCode();
		String subscriptionCode = addClientSubscriptionRequest.getSubscriptionCode();

		Subscription subscription = getSubscription(productCode, subscriptionCode);

		// TODO(fsabino): implement JWT and extract the identification from the token body (after client login)
		Client client = clientRepository.getClientByIdentification("identification").orElseGet(() -> null);

		// TODO(fsabino): validate if the client already has an ACTIVE client subscription
		// @formatter:off
		ClientSubscription clientSubscription = ClientSubscription.builder()
				.client(client)
				.subscription(subscription)
				.code(RandomStringUtils.randomNumeric(8))
				.startDate(DateTime.now())
				.endDate(calculateEndDate(subscription.getType()))
				.build();
		// @formatter:on
		logger.info("addClientSubscription - ClientSubscription added Subscription={} Product={} Client={}",
				subscriptionCode, productCode, client.getIdentification());
		ClientSubscription newClientSubscription =
				clientSubscriptionRepository.addClientSubscription(clientSubscription);

		clientSubscriptionStatusService.addClientSubscriptionStatus(newClientSubscription, Status.ACTIVE);

		// @formatter:off
		return ClientSubscriptionCodeDTO.builder()
				.code(Long.parseLong(newClientSubscription.getCode()))
				.build();
		// @formatter:on
	}

	@Override
	public List<ClientSubscriptionDTO> getActiveClientSubscriptions() {
		List<ClientSubscription> clientSubscriptions = clientSubscriptionRepository.getClientSubscriptions();

		// @formatter:off
		List<ClientSubscriptionDTO> clientSubscriptionDTOs = clientSubscriptions.stream()
				.filter(clientSubscription -> clientSubscriptionStatusService.isActiveClientSubscription(clientSubscription.getId()))
				.map(clientSubscription -> {
							Optional<ClientSubscriptionStatus> maybeClientSubscriptionStatus =
									clientSubscriptionStatusService.getCurrentClientSubscriptionStatus(clientSubscription.getId());

							return clientSubscriptionHelper.modelToDTO(clientSubscription, maybeClientSubscriptionStatus.get());
				})
				.collect(Collectors.toList());
		// @formatter:on

		return clientSubscriptionDTOs;
	}

	@Override
	public ClientSubscriptionCodeDTO updateStatusClientSubscription(String clientSubscriptionCode,
			UpdateStatusClientSubscriptionRequest updateStatusClientSubscriptionRequest)
			throws ClientSubscriptionNotFoundException {
		// TODO(fsabino): validate status enum
		Status status = Status.valueOf(updateStatusClientSubscriptionRequest.getStatus());

		logger.info("updateStatusClientSubscription - clientSubscription code={} status={}", clientSubscriptionCode,
				status.toString());
		return updateStatusClientSubscription(clientSubscriptionCode, status);
	}

	@Override
	public ClientSubscriptionCodeDTO cancelClientSubscription(String clientSubscriptionCode)
			throws ClientSubscriptionNotFoundException {

		logger.info("cancelClientSubscription - clientSubscription code={}", clientSubscriptionCode);
		return updateStatusClientSubscription(clientSubscriptionCode, Status.CANCELLED);
	}

	Subscription getSubscription(String productCode, String subscriptionCode)
			throws IlegalProductCodeException, IlegalSubscriptionCodeException, SubscriptionNotFoundException {
		if (StringUtils.isBlank(productCode)) {
			logger.error("Product Code is empty");
			throw new IlegalProductCodeException("Product Code is empty");
		}
		if (StringUtils.isBlank(subscriptionCode)) {
			logger.error("Subscription Code is empty");
			throw new IlegalSubscriptionCodeException("Subscription Code is empty");
		}
		return subscriptionService.getSubscriptionByCodeAndProductCode(subscriptionCode, productCode)
				.orElseThrow(() -> {
					logger.error("Subscription could not be found. ProductCode={} SubscriptionCode={}", productCode,
							subscriptionCode);
					return new SubscriptionNotFoundException(
							String.format("Subscription could not be found. ProductCode=%s SubscriptionCode=%s",
									productCode, subscriptionCode));
				});
	}

	ClientSubscriptionCodeDTO updateStatusClientSubscription(String clientSubscriptionCode, Status status)
			throws ClientSubscriptionNotFoundException {
		// @formatter:off
		ClientSubscription clientSubscription =
				clientSubscriptionRepository.getClientSubscriptionsByCode(clientSubscriptionCode)
				.orElseThrow(() -> {
					logger.error("ClientSubscription code={} could not be found.", clientSubscriptionCode);
					return new ClientSubscriptionNotFoundException(
							String.format("ClientSubscription code=%s could not be found.", clientSubscriptionCode));
				});
		// @formatter:on

		clientSubscriptionStatusService.addClientSubscriptionStatus(clientSubscription, status);

		// @formatter:off
		return ClientSubscriptionCodeDTO.builder()
				.code(Long.parseLong(clientSubscription.getCode()))
				.build();
		// @formatter:on
	}

	private DateTime calculateEndDate(Type type) {
		DateTime endDate = DateTime.now();
		switch (type) {
			case MONTH:
				endDate = endDate.plusMonths(1);
				break;
			case YEAR:
				endDate = endDate.plusYears(1);
				break;
			case SIX_MONTH:
				endDate = endDate.plusMonths(6);
				break;
			case DAY:
				endDate = endDate.plusDays(1);
				break;
			case WEEK:
				endDate = endDate.plusWeeks(1);
				break;
			default:
				throw new IllegalArgumentException(String.format("Type=%s could is not defined", type));
		}
		return endDate;
	}
}
