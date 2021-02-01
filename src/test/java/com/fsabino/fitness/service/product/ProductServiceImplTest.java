package com.fsabino.fitness.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fsabino.fitness.dto.ProductDTO;
import com.fsabino.fitness.exception.ProductNotFoundException;
import com.fsabino.fitness.helper.ProductHelper;
import com.fsabino.fitness.persistence.Product;
import com.fsabino.fitness.repository.product.ProductRepository;
import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepositoryMock;

	@Mock
	private ProductHelper productHelperMock;

	@InjectMocks
	private ProductServiceImpl subject;

	@Test
	public void given_IHaveProducts_when_getAllProducts_then_TheProductsAreReturned() {
		// given
		Product productMock = Mockito.mock(Product.class);

		// @formatter:off
		List<Product> products = ImmutableList.<Product> builder()
				.add(productMock)
				.add(productMock)
				.build();
		// @formatter:on

		when(productRepositoryMock.getAllProducts()).thenReturn(products);
		when(productHelperMock.modelToDTO(eq(productMock))).thenReturn(Mockito.mock(ProductDTO.class));

		// when
		List<ProductDTO> result = subject.getAllProducts();

		// then
		assertThat(result).hasSize(2);

		verify(productHelperMock, times(2)).modelToDTO(eq(productMock));
	}

	@Test
	public void given_IhaveNoProducts_when_getAllProducts_then_AnEmptyListIsReturned() {
		// given
		List<Product> products = ImmutableList.<Product> builder().build();

		when(productRepositoryMock.getAllProducts()).thenReturn(products);

		// when
		List<ProductDTO> result = subject.getAllProducts();

		// then
		assertThat(result).isEmpty();

		verify(productHelperMock, never()).modelToDTO(any(Product.class));
	}

	@Test
	public void given_IHaveAValidCode_when_getProductByCode_then_TheProductsIsReturned()
			throws ProductNotFoundException {
		// given
		String code = RandomStringUtils.randomAlphanumeric(1, 10);
		Product productMock = Mockito.mock(Product.class);
		Optional<Product> maybeProduct = Optional.of(productMock);

		when(productRepositoryMock.getProductByCode(eq(code))).thenReturn(maybeProduct);
		when(productHelperMock.modelToDTO(eq(productMock))).thenReturn(Mockito.mock(ProductDTO.class));

		// when
		ProductDTO result = subject.getProductByCode(code);

		// then
		assertThat(result).isNotNull();

		verify(productHelperMock, times(1)).modelToDTO(eq(productMock));
	}

	@Test
	public void given_IHaveAnInvalidCode_when_getProductByCode_then_ProductNotFoundExceptionIsThrown()
			throws ProductNotFoundException {
		// given
		String code = RandomStringUtils.randomAlphanumeric(1, 10);
		Optional<Product> maybeProduct = Optional.empty();

		when(productRepositoryMock.getProductByCode(eq(code))).thenReturn(maybeProduct);

		// when
		assertThatThrownBy(() -> subject.getProductByCode(code)).isExactlyInstanceOf(ProductNotFoundException.class)
				.hasMessage(String.format("Product with code=%s could not be found", code));

		// then
		verify(productHelperMock, never()).modelToDTO(any(Product.class));
	}
}