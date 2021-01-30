package com.fsabino.fitness.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.fsabino.fitness.persistence.Product;
import com.fsabino.fitness.persistence.Subscription;
import com.fsabino.fitness.persistence.Subscription.Type;
import com.google.common.collect.ImmutableList;

@Repository
public class ProductRepositoryMockImpl implements ProductRepository {
	
	private final List<Subscription> SUBSCRIPTION_ABS = ImmutableList.<Subscription>builder()
			.add(createSubscription(1L, "SUBABSMONTH", "Month subscription", 80, 7, Type.MONTH))
			.add(createSubscription(2L, "SUBABSDAY", "Day subscription", 8, 7, Type.DAY))
			.add(createSubscription(3L, "SUBABSYEAR", "Year subscription", 480, 7, Type.YEAR))
			.build();
	
	private final List<Subscription> SUBSCRIPTION_JOGA = ImmutableList.<Subscription>builder()
			.add(createSubscription(6L, "SUBYOGAYEAR", "Year subscription", 350, 7, Type.YEAR))
			.build();
	
	private final List<Subscription> SUBSCRIPTION_MUSCLE = ImmutableList.<Subscription>builder()
			.add(createSubscription(7L, "SUBMUSCLEYEAR", "Year subscription", 550, 7, Type.YEAR))
			.build();

	private final List<Product> PRODUCTS = ImmutableList.<Product> builder()
			.add(createProduct(1L, "AA1", "ABS CLASSES", "Monday to Friday", SUBSCRIPTION_ABS))
			.add(createProduct(2L, "AA2", "YOGA", "Monday to Friday", SUBSCRIPTION_JOGA))
			.add(createProduct(3L, "AA3", "MUSCLE STUDIO", "Monday to Monday", SUBSCRIPTION_MUSCLE))
			.build();
	
	@Override
	public List<Product> getAllProducts() {
		return PRODUCTS;
	}
	
	@Override
	public Optional<Product> getProductByCode(String code) {
		return PRODUCTS.stream()
				.filter(product -> StringUtils.equals(product.getCode(), code))
				.findFirst();
	}
	
	private Product createProduct(Long id, String code, String name, String description, List<Subscription> subscriptions) {
		return Product.builder()
				.id(id)
				.code(code)
				.description(description)
				.name(name)
				.subscriptions(subscriptions)
				.build();
	}
	
	private Subscription createSubscription(Long id, String code, String description, double price, double tax, Type type) {
		return Subscription.builder()
				.id(id)
				.code(code)
				.description(description)
				.price(price)
				.tax(tax)
				.type(type)
				.build();
	}
}
