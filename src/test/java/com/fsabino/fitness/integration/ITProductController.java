package com.fsabino.fitness.integration;

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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fsabino.fitness.IntegrationApplication;
import com.fsabino.fitness.controller.ProductController;
import com.fsabino.fitness.dto.ProductDTO;
import com.fsabino.fitness.dto.SubscriptionDTO;
import com.fsabino.fitness.exception.ProductNotFoundException;
import com.fsabino.fitness.service.product.ProductService;
import com.google.common.collect.ImmutableList;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = IntegrationApplication.class)
@WebMvcTest(ProductController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets", uriHost = "localhost:8791/fitness",
	uriScheme = "https", uriPort = 443)
@ActiveProfiles("integration")
public class ITProductController {

	private static final OperationResponsePreprocessor PREPROCESS_RESPONSE = preprocessResponse(prettyPrint(),
			removeHeaders("X-Content-Type-Options", "X-XSS-Protection", "Cache-Control", "Pragma", "Expires",
					"Strict-Transport-Security", "X-Frame-Options", "X-Application-Context"));

	private static final OperationRequestPreprocessor PREPROCESS_REQUEST = preprocessRequest(prettyPrint(),
			removeHeaders("X-Content-Type-Options", "X-XSS-Protection", "Cache-Control", "Pragma", "Expires",
					"Strict-Transport-Security", "X-Frame-Options", "X-Application-Context"));

	@MockBean
	private ProductService productServiceMock; 
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void testGetProducts() throws Exception {
		when(productServiceMock.getAllProducts()).thenReturn(createProducts());

		mvc.perform(RestDocumentationRequestBuilders.get("/product/v1"))
				.andExpect(status().isOk())
				.andDo(document("products", PREPROCESS_REQUEST, PREPROCESS_RESPONSE,
				responseFields(
						fieldWithPath("[].code").description("Product Code"),
						fieldWithPath("[].name").description("Product Name"),
						fieldWithPath("[].description").description("Product Description"),
						fieldWithPath("[].message").description("Message").ignored(),
						fieldWithPath("[].subscriptions[]").description("Subscriptions of the product"),
						fieldWithPath("[].subscriptions[].code").description("Code Subscription"),
						fieldWithPath("[].subscriptions[].description").description("Description Subscription"),
						fieldWithPath("[].subscriptions[].type").description("Type Subscription"),
						fieldWithPath("[].subscriptions[].price").description("Price Subscription"),
						fieldWithPath("[].subscriptions[].tax").description("Tax Subscription"),
						fieldWithPath("[].subscriptions[].priceTotal").description("Price Total Subscription (Price + Tax)"),
						fieldWithPath("[].subscriptions[].fee").description("Fee Subscription (Per Month)"),
						fieldWithPath("[].subscriptions[].message").description("Message").ignored())));
	}
	
	@Test
	public void testGetProduct() throws Exception {
		String code = "AA1";
		
		when(productServiceMock.getProductByCode(eq(code))).thenReturn(createProduct());

		mvc.perform(RestDocumentationRequestBuilders.get("/product/v1/{code}", code))
				.andExpect(status().isOk())
				.andDo(document("product", PREPROCESS_REQUEST, PREPROCESS_RESPONSE,
					pathParameters(parameterWithName("code").description("Code Product")),
					responseFields(
							fieldWithPath("code").description("Product Code"),
							fieldWithPath("name").description("Product Name"),
							fieldWithPath("message").description("Message").ignored(),
							fieldWithPath("description").description("Product Description"),
							fieldWithPath("subscriptions[]").description("Subscriptions of the product"),
							fieldWithPath("subscriptions[].code").description("Code Subscription"),
							fieldWithPath("subscriptions[].description").description("Description Subscription"),
							fieldWithPath("subscriptions[].type").description("Type Subscription"),
							fieldWithPath("subscriptions[].price").description("Price Subscription"),
							fieldWithPath("subscriptions[].tax").description("Tax Subscription"),
							fieldWithPath("subscriptions[].priceTotal").description("Price Total Subscription (Price + Tax)"),
							fieldWithPath("subscriptions[].fee").description("Fee Subscription (Per Month)"),
							fieldWithPath("subscriptions[].message").description("Message").ignored())));
	}
	
	@Test
	public void testGetProductNotFound() throws Exception {
		String code = "99999999";
		
		doThrow(ProductNotFoundException.class).when(productServiceMock).getProductByCode(eq(code));

		mvc.perform(RestDocumentationRequestBuilders.get("/product/v1/{code}", code))
				.andExpect(status().isNotFound())
				.andDo(document("product-notfound", PREPROCESS_REQUEST, PREPROCESS_RESPONSE,
					pathParameters(parameterWithName("code").description("Code Product")),
					responseFields(
							fieldWithPath("message").description("Message"),
							fieldWithPath("code").description("Product Code").ignored(),
							fieldWithPath("name").description("Product Name").ignored(),
							fieldWithPath("description").description("Product Description").ignored(),
							fieldWithPath("subscriptions").description("Subscriptions of the product").ignored())));
	}
	
	private ProductDTO createProduct() {
		SubscriptionDTO monthSubscription = createSubscription("SUBABSMONTH", "Month subscription", 80, 7, "MONTH", "85,60 €", "85,60 €");
		SubscriptionDTO yearSubscription = createSubscription("SUBABSYEAR", "Year subscription", 480, 7, "YEAR", "513,60 €", "42,80 €");
		
		return createProduct("AA1", "ABS CLASSES", "Monday to Friday", List.of(monthSubscription, yearSubscription));
	}

	private List<ProductDTO> createProducts() {
		SubscriptionDTO monthSubscription = createSubscription("SUBABSMONTH", "Month subscription", 80, 7, "MONTH", "85,60 €", "85,60 €");
		SubscriptionDTO yearSubscription = createSubscription("SUBABSYEAR", "Year subscription", 480, 7, "YEAR", "513,60 €", "42,80 €");
		
		return ImmutableList.<ProductDTO>builder()
				.add(createProduct("AA1", "ABS CLASSES", "Monday to Friday", List.of(monthSubscription, yearSubscription)))
				.build();
	}
	
	private ProductDTO createProduct(String code, String name, String description, List<SubscriptionDTO> subscriptions) {
		return ProductDTO.builder()
				.code(code)
				.description(description)
				.name(name)
				.subscriptions(subscriptions)
				.build();
	}
	
	private SubscriptionDTO createSubscription(String code, String description, double price, double tax, String type, String fee, String priceTotal) {
		return SubscriptionDTO.builder()
				.code(code)
				.type(type)
				.description(description)
				.price(price)
				.tax(tax)
				.priceTotal(priceTotal)
				.fee(fee)
				.build();
	}
}
