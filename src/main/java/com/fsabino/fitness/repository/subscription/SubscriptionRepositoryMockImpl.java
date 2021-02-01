package com.fsabino.fitness.repository.subscription;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.fsabino.fitness.persistence.Product;
import com.fsabino.fitness.persistence.Subscription;
import com.fsabino.fitness.persistence.Subscription.Type;
import com.google.common.collect.ImmutableList;

@Repository
public class SubscriptionRepositoryMockImpl implements SubscriptionRepository {

	private final static Product PRODUCT_AA1 = createProduct(1L, "AA1", "ABS CLASSES", "Monday to Friday");
	private final static Product PRODUCT_AA2 = createProduct(2L, "AA2", "YOGA", "Monday to Friday");
	private final static Product PRODUCT_AA3 = createProduct(3L, "AA3", "MUSCLE STUDIO", "Monday to Monday");
	
	private final static List<Subscription> SUBSCRIPTIONS = ImmutableList.<Subscription>builder()
			.add(createSubscription(1L, "SUBABSMONTH", "Month subscription", 80, 7, Type.MONTH, PRODUCT_AA1))
			.add(createSubscription(2L, "SUBABSDAY", "Day subscription", 8, 7, Type.DAY, PRODUCT_AA1))
			.add(createSubscription(3L, "SUBABSYEAR", "Year subscription", 480, 7, Type.YEAR, PRODUCT_AA1))
			.add(createSubscription(6L, "SUBYOGAYEAR", "Year subscription", 350, 7, Type.YEAR, PRODUCT_AA2))
			.add(createSubscription(7L, "SUBMUSCLEYEAR", "Year subscription", 550, 7, Type.YEAR, PRODUCT_AA3))
			.build();
	
	@Override
	public Optional<Subscription> getSubscriptionByCodeAndProductCode(String subscriptionCode, String productCode) {
		return SUBSCRIPTIONS.stream()
			.filter(subscription -> StringUtils.equals(subscription.getProduct().getCode(), productCode))
			.filter(subscription -> StringUtils.equals(subscription.getCode(), subscriptionCode))
			.findFirst();
	}
	
	private static Product createProduct(Long id, String code, String name, String description) {
		return Product.builder()
				.id(id)
				.code(code)
				.description(description)
				.name(name)
				.build();
	}
	
	private static Subscription createSubscription(Long id, String code, String description, double price, double tax, Type type, Product product) {
		return Subscription.builder()
				.id(id)
				.code(code)
				.description(description)
				.price(price)
				.tax(tax)
				.type(type)
				.product(product)
				.build();
	}
}
