package com.fsabino.fitness.service.subscription;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fsabino.fitness.dto.ClientSubscriptionCodeDTO;
import com.fsabino.fitness.exception.ClientSubscriptionNotFoundException;
import com.fsabino.fitness.exception.IlegalProductCodeException;
import com.fsabino.fitness.exception.IlegalSubscriptionCodeException;
import com.fsabino.fitness.exception.SubscriptionNotFoundException;
import com.fsabino.fitness.helper.ClientSubscriptionHelper;
import com.fsabino.fitness.persistence.Client;
import com.fsabino.fitness.persistence.ClientSubscription;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus.Status;
import com.fsabino.fitness.persistence.Subscription;
import com.fsabino.fitness.persistence.Subscription.Type;
import com.fsabino.fitness.repository.client.ClientRepository;
import com.fsabino.fitness.repository.subscription.ClientSubscriptionRepository;
import com.fsabino.fitness.request.AddClientSubscriptionRequest;

@RunWith(MockitoJUnitRunner.class)
public class ClientSubscriptionServiceImplTest {

	@Mock
	private SubscriptionService subscriptionServiceMock;

	@Mock
	private ClientRepository clientRepositoryMock;

	@Mock
	private ClientSubscriptionStatusService clientSubscriptionStatusServiceMock;

	@Mock
	private ClientSubscriptionRepository clientSubscriptionRepositoryMock;

	@Mock
	private ClientSubscriptionHelper clientSubscriptionHelperMock;

	@InjectMocks
	private ClientSubscriptionServiceImpl subject;

	@Test
	public void given_IhaveValidProductCodeAndSubscriptionCode_when_getSubscription_then_SubscriptionIsReturned()
			throws IlegalProductCodeException, IlegalSubscriptionCodeException, SubscriptionNotFoundException {
		// given
		String productCode = RandomStringUtils.randomAlphabetic(10);
		String subscriptionCode = RandomStringUtils.randomAlphabetic(10);
		Subscription subscriptionMock = Mockito.mock(Subscription.class);
		Optional<Subscription> maybeSubscription = Optional.of(subscriptionMock);

		when(subscriptionServiceMock.getSubscriptionByCodeAndProductCode(eq(subscriptionCode), eq(productCode)))
				.thenReturn(maybeSubscription);

		// when
		Subscription result = subject.getSubscription(productCode, subscriptionCode);

		// then
		assertThat(result).isNotNull();
	}

	@Test
	public void
			given_IhaveInValidProductCodeAndSubscriptionCode_when_getSubscription_then_SubscriptionNotFoundExceptionIsThrown()
					throws IlegalProductCodeException, IlegalSubscriptionCodeException, SubscriptionNotFoundException {
		// given
		String productCode = RandomStringUtils.randomAlphabetic(10);
		String subscriptionCode = RandomStringUtils.randomAlphabetic(10);
		Optional<Subscription> maybeSubscription = Optional.empty();

		when(subscriptionServiceMock.getSubscriptionByCodeAndProductCode(eq(subscriptionCode), eq(productCode)))
				.thenReturn(maybeSubscription);

		// when
		assertThatThrownBy(() -> subject.getSubscription(productCode, subscriptionCode))
				.isExactlyInstanceOf(SubscriptionNotFoundException.class)
				.hasMessage(String.format("Subscription could not be found. ProductCode=%s SubscriptionCode=%s",
						productCode, subscriptionCode));
	}

	@Test
	public void given_IhaveProductCodeEmpty_when_getSubscription_then_IlegalProductCodeExceptionIsThrown()
			throws IlegalProductCodeException, IlegalSubscriptionCodeException, SubscriptionNotFoundException {
		// given
		String productCode = StringUtils.EMPTY;

		// when
		assertThatThrownBy(() -> subject.getSubscription(productCode, StringUtils.EMPTY))
				.isExactlyInstanceOf(IlegalProductCodeException.class).hasMessage("Product Code is empty");

		verify(subscriptionServiceMock, never()).getSubscriptionByCodeAndProductCode(anyString(), anyString());
	}

	@Test
	public void given_IhaveSubscriptionCodeEmpty_when_getSubscription_then_IlegalSubscriptionCodeExceptionIsThrown()
			throws IlegalProductCodeException, IlegalSubscriptionCodeException, SubscriptionNotFoundException {
		// given
		String productCode = RandomStringUtils.randomAlphabetic(10);
		String subscriptionCode = StringUtils.EMPTY;

		// when
		assertThatThrownBy(() -> subject.getSubscription(productCode, subscriptionCode))
				.isExactlyInstanceOf(IlegalSubscriptionCodeException.class).hasMessage("Subscription Code is empty");

		verify(subscriptionServiceMock, never()).getSubscriptionByCodeAndProductCode(anyString(), anyString());
	}

	@Test
	public void given_CorrectAddClientSubscriptionRequest_when_addClientSubscription_then_ClientSubscriptionIsCreated()
			throws IlegalProductCodeException, IlegalSubscriptionCodeException, SubscriptionNotFoundException {
		// given
		ClientSubscriptionServiceImpl spySubject = Mockito.spy(subject);

		String productCode = RandomStringUtils.randomAlphabetic(10);
		String subscriptionCode = RandomStringUtils.randomAlphabetic(10);
		String identification = "identification";

		AddClientSubscriptionRequest request = new AddClientSubscriptionRequest();
		request.setProductCode(productCode);
		request.setSubscriptionCode(subscriptionCode);

		Subscription subscription = Subscription.builder().type(Type.WEEK).build();

		Client client = Client.builder().identification(RandomStringUtils.randomAlphabetic(10)).build();
		Optional<Client> maybeClient = Optional.of(client);

		ClientSubscription clientSubscription =
				ClientSubscription.builder().code(RandomStringUtils.randomNumeric(5)).build();

		doReturn(subscription).when(spySubject).getSubscription(eq(productCode), eq(subscriptionCode));
		when(clientRepositoryMock.getClientByIdentification(eq(identification))).thenReturn(maybeClient);
		when(clientSubscriptionRepositoryMock.addClientSubscription(any(ClientSubscription.class)))
				.thenReturn(clientSubscription);
		doNothing().when(clientSubscriptionStatusServiceMock).addClientSubscriptionStatus(eq(clientSubscription),
				eq(Status.ACTIVE));

		// when
		ClientSubscriptionCodeDTO result = spySubject.addClientSubscription(request);

		// then
		assertThat(result.getCode()).isEqualTo(Long.parseLong(clientSubscription.getCode()));
	}

	@Test
	public void
			given_IhaveClientSubscriptionCode_when_updateStatusClientSubscription_then_ClientSubscriptionStatusIsAdded()
					throws ClientSubscriptionNotFoundException {
		// given
		String clientSubscriptionCode = RandomStringUtils.randomNumeric(10);
		Status status = Status.PAUSED;

		ClientSubscription clientSubscription = ClientSubscription.builder().code(clientSubscriptionCode).build();
		Optional<ClientSubscription> maybeClientSubscription = Optional.of(clientSubscription);

		when(clientSubscriptionRepositoryMock.getClientSubscriptionsByCode(eq(clientSubscriptionCode)))
				.thenReturn(maybeClientSubscription);
		doNothing().when(clientSubscriptionStatusServiceMock).addClientSubscriptionStatus(eq(clientSubscription),
				eq(status));

		// when
		ClientSubscriptionCodeDTO result = subject.updateStatusClientSubscription(clientSubscriptionCode, status);

		// then
		assertThat(result.getCode()).isEqualTo(Long.parseLong(clientSubscriptionCode));
	}

	@Test
	public void
			given_IhaveInvalidClientSubscriptionCode_when_updateStatusClientSubscription_then_ClientSubscriptionNotFoundExceptionIsThrown()
					throws ClientSubscriptionNotFoundException {
		// given
		String clientSubscriptionCode = RandomStringUtils.randomNumeric(10);
		Status status = Status.PAUSED;

		Optional<ClientSubscription> maybeClientSubscription = Optional.empty();

		when(clientSubscriptionRepositoryMock.getClientSubscriptionsByCode(eq(clientSubscriptionCode)))
				.thenReturn(maybeClientSubscription);

		// when
		assertThatThrownBy(() -> subject.updateStatusClientSubscription(clientSubscriptionCode, status))
				.isExactlyInstanceOf(ClientSubscriptionNotFoundException.class)
				.hasMessage(String.format("ClientSubscription code=%s could not be found.", clientSubscriptionCode));

		verify(clientSubscriptionStatusServiceMock, never()).addClientSubscriptionStatus(any(ClientSubscription.class),
				eq(status));
	}
}
