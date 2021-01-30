package com.fsabino.fitness.helper;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.fsabino.fitness.dto.SubscriptionDTO;
import com.fsabino.fitness.persistence.Subscription;
import com.fsabino.fitness.persistence.Subscription.Type;

@Component
public class SubscriptionHelper {

	private final PriceHelper priceHelper;
	
	@Inject
	public SubscriptionHelper(PriceHelper priceHelper) {
		this.priceHelper = priceHelper;
	}

	public SubscriptionDTO modelToDTO(Subscription subscription) {
		BigDecimal priceWithTax = priceHelper.preparePrice(subscription.getPrice(), subscription.getTax()); 
		
		int numberToSplit = getNumberToSplit(subscription.getType());
		BigDecimal feePrice = priceHelper.divide(priceWithTax, numberToSplit);
		
		String priceTotal = priceHelper.transform(priceWithTax);
		String fee = priceHelper.transform(feePrice);
		
		SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
				.code(subscription.getCode())
				.description(subscription.getDescription())
				.price(subscription.getPrice())
				.tax(subscription.getTax())
				.type(subscription.getType().toString())
				.priceTotal(priceTotal)
				.fee(fee)
				.build();
		return subscriptionDTO;
	}
	
	private int getNumberToSplit(Type type) {
		int numberToSplit = 1;
		switch (type) {
			case YEAR:
				numberToSplit = 12;
				break;
			case SIX_MONTH:
				numberToSplit = 6;
				break;
			default:
				break;
		}
		return numberToSplit;
	}
}
