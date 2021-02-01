package com.fsabino.fitness.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsabino.fitness.IntegrationApplication;
import com.fsabino.fitness.controller.ClientSubscriptionController;
import com.fsabino.fitness.dto.ClientDTO;
import com.fsabino.fitness.dto.ClientSubscriptionCodeDTO;
import com.fsabino.fitness.dto.ClientSubscriptionDTO;
import com.fsabino.fitness.dto.ClientSubscriptionStatusDTO;
import com.fsabino.fitness.dto.ProductDTO;
import com.fsabino.fitness.dto.SubscriptionDTO;
import com.fsabino.fitness.exception.IlegalProductCodeException;
import com.fsabino.fitness.exception.SubscriptionNotFoundException;
import com.fsabino.fitness.persistence.ClientSubscriptionStatus.Status;
import com.fsabino.fitness.request.AddClientSubscriptionRequest;
import com.fsabino.fitness.request.UpdateStatusClientSubscriptionRequest;
import com.fsabino.fitness.service.subscription.ClientSubscriptionService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = IntegrationApplication.class)
@WebMvcTest(ClientSubscriptionController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets", uriHost = "localhost:8791/fitness",
	uriScheme = "https", uriPort = 443)
@ActiveProfiles("integration")
public class ITClientSubscriptionController {

	private static final OperationResponsePreprocessor PREPROCESS_RESPONSE = preprocessResponse(prettyPrint(),
			removeHeaders("X-Content-Type-Options", "X-XSS-Protection", "Cache-Control", "Pragma", "Expires",
					"Strict-Transport-Security", "X-Frame-Options", "X-Application-Context"));

	private static final OperationRequestPreprocessor PREPROCESS_REQUEST = preprocessRequest(prettyPrint(),
			removeHeaders("X-Content-Type-Options", "X-XSS-Protection", "Cache-Control", "Pragma", "Expires",
					"Strict-Transport-Security", "X-Frame-Options", "X-Application-Context"));

	@MockBean
	private ClientSubscriptionService clientSubscriptionServiceMock; 
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testAddClientSubscription() throws Exception {
		// Given
		AddClientSubscriptionRequest addClientSubscriptionRequest = new AddClientSubscriptionRequest();
		addClientSubscriptionRequest.setProductCode("AA1");
		addClientSubscriptionRequest.setSubscriptionCode("SUBABSYEAR");
		
		ClientSubscriptionCodeDTO clientSubscriptionCodeDTO = ClientSubscriptionCodeDTO.builder()
				.code(RandomUtils.nextLong())
				.build();
		
		// When
		when(clientSubscriptionServiceMock.addClientSubscription(any(AddClientSubscriptionRequest.class)))
			.thenReturn(clientSubscriptionCodeDTO);
		
		String content = new ObjectMapper().writeValueAsString(addClientSubscriptionRequest);
		
		// Then
		mvc.perform(RestDocumentationRequestBuilders.post("/subscription/v1")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andDo(document("add-subscription", PREPROCESS_REQUEST, PREPROCESS_RESPONSE,
					requestFields(
						fieldWithPath("productCode").description("Product Code"),
						fieldWithPath("subscriptionCode").description("Subscription Code")),
					responseFields(
						fieldWithPath("code").description("Client Subscription Code generated"),
						fieldWithPath("message").description("Error Message").ignored())));
	}
	
	@Test
	public void testAddClientSubscriptionInvalidArgument() throws Exception {
		// Given
		AddClientSubscriptionRequest addClientSubscriptionRequest = new AddClientSubscriptionRequest();
		addClientSubscriptionRequest.setProductCode("");
		addClientSubscriptionRequest.setSubscriptionCode("SUBABSYEAR");
		
		// When
		doThrow(IlegalProductCodeException.class).when(clientSubscriptionServiceMock)
			.addClientSubscription(any(AddClientSubscriptionRequest.class));
		
		String content = new ObjectMapper().writeValueAsString(addClientSubscriptionRequest);
		
		// Then
		mvc.perform(RestDocumentationRequestBuilders.post("/subscription/v1")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isUnprocessableEntity())
			.andDo(document("add-subscription-unprocessable", PREPROCESS_REQUEST, PREPROCESS_RESPONSE,
					responseFields(
							fieldWithPath("code").description("Client Subscription Code generated").ignored(),
							fieldWithPath("message").description("Error Message"))));
	}
	
	@Test
	public void testAddClientSubscriptionNotFound() throws Exception {
		// Given
		AddClientSubscriptionRequest addClientSubscriptionRequest = new AddClientSubscriptionRequest();
		addClientSubscriptionRequest.setProductCode("999999");
		addClientSubscriptionRequest.setSubscriptionCode("SUBABSYEAR");
		
		// When
		doThrow(SubscriptionNotFoundException.class).when(clientSubscriptionServiceMock)
			.addClientSubscription(any(AddClientSubscriptionRequest.class));
		
		String content = new ObjectMapper().writeValueAsString(addClientSubscriptionRequest);
		
		// Then
		mvc.perform(RestDocumentationRequestBuilders.post("/subscription/v1")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isInternalServerError())
			.andDo(document("add-subscription-notfound", PREPROCESS_REQUEST, PREPROCESS_RESPONSE,
			responseFields(
					fieldWithPath("code").description("Client Subscription Code generated").ignored(),
					fieldWithPath("message").description("Error Message"))));
	}
	
	@Test
	public void testGetClientSubscriptions() throws Exception {
		// Given When
		when(clientSubscriptionServiceMock.getActiveClientSubscriptions()).thenReturn(createSubscriptions());

		// Then
		mvc.perform(RestDocumentationRequestBuilders.get("/subscription/v1"))
				.andExpect(status().isOk())
				.andDo(document("get-subscriptions", PREPROCESS_REQUEST, PREPROCESS_RESPONSE));
	}
	
	@Test
	public void testPauseClientSubscription() throws Exception {
		// Given
		String clientSubscriptionCode = RandomStringUtils.randomNumeric(8);
		
		UpdateStatusClientSubscriptionRequest updateStatusClientSubscriptionRequest = new UpdateStatusClientSubscriptionRequest();
		updateStatusClientSubscriptionRequest.setStatus(Status.PAUSED.toString());
		
		ClientSubscriptionCodeDTO clientSubscriptionCodeDTO = ClientSubscriptionCodeDTO.builder()
				.code(Long.parseLong(clientSubscriptionCode))
				.build();
		
		// When
		when(clientSubscriptionServiceMock.updateStatusClientSubscription(eq(clientSubscriptionCode), 
				any(UpdateStatusClientSubscriptionRequest.class))).thenReturn(clientSubscriptionCodeDTO);
		
		String content = new ObjectMapper().writeValueAsString(updateStatusClientSubscriptionRequest);
		
		// Then
		mvc.perform(RestDocumentationRequestBuilders.put("/subscription/v1/{clientSubscriptionCode}", clientSubscriptionCode)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andDo(document("put-subscription", PREPROCESS_REQUEST, PREPROCESS_RESPONSE,
					requestFields(	
						fieldWithPath("status").description("ACTIVE | PAUSED")),
					responseFields(
						fieldWithPath("code").description("Client Subscription Code"),
						fieldWithPath("message").description("Error Message").ignored())));
	}
	
	@Test
	public void testCancelClientSubscription() throws Exception {
		// Given
		String clientSubscriptionCode = RandomStringUtils.randomNumeric(8);
		
		ClientSubscriptionCodeDTO clientSubscriptionCodeDTO = ClientSubscriptionCodeDTO.builder()
				.code(Long.parseLong(clientSubscriptionCode))
				.build();
		// When
		when(clientSubscriptionServiceMock.cancelClientSubscription(eq(clientSubscriptionCode)))
			.thenReturn(clientSubscriptionCodeDTO);
		
		// Then
		mvc.perform(RestDocumentationRequestBuilders.delete("/subscription/v1/{clientSubscriptionCode}", clientSubscriptionCode))
			.andExpect(status().isOk())
			.andDo(document("delete-subscription", PREPROCESS_REQUEST, PREPROCESS_RESPONSE,
					responseFields(
						fieldWithPath("code").description("Client Subscription Code"),
						fieldWithPath("message").description("Error Message").ignored())));
	}

	private List<ClientSubscriptionDTO> createSubscriptions() {
		ClientSubscriptionDTO clientSubscriptionDTO = ClientSubscriptionDTO.builder()
				.code(RandomStringUtils.randomNumeric(10))
				.client(ClientDTO.builder()
						.firstName("firstName")
						.lastName("lastName")
						.identification("identification")
						.build())
				.startDate(DateTime.now())
				.endDate(DateTime.now().plusYears(1))
				.clientSubscriptionStatus(ClientSubscriptionStatusDTO.builder()
											.status(Status.ACTIVE.toString())
											.startDate(DateTime.now())
											.build())
				.product(ProductDTO.builder()
							.code(RandomStringUtils.randomNumeric(10))
							.name("name")
							.build())
				.subscription(SubscriptionDTO.builder()
							.code(RandomStringUtils.randomNumeric(10))
							.description("description")
							.build())
				.build();
		
		return List.of(clientSubscriptionDTO);
	}
}
