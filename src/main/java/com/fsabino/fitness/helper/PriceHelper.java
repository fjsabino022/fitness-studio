package com.fsabino.fitness.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class PriceHelper {

	private static final Locale LOCALE = Locale.GERMANY;
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(LOCALE);

	public BigDecimal preparePrice(double priceInEuro, double vat) {
		return BigDecimal.valueOf(priceInEuro + (priceInEuro * (vat / 100.0))).setScale(4, RoundingMode.HALF_UP);
	}
	
	public BigDecimal divide(BigDecimal priceInEuro, int number) {
		BigDecimal result = priceInEuro.divide(BigDecimal.valueOf(number), RoundingMode.HALF_UP);
		return result;
	}
	
	public String transform(BigDecimal price) {
		if (price == null) {
			return null;
		}

		return transform(calculate(price));
	}
	
	private Long calculate(BigDecimal price) {
		if (price == null) {
			return null;
		}

		return price.setScale(4, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(100)).longValue();
	}

	private String transform(Long price) {
		double priceDobule = (double) price / 100d;

		return CURRENCY_FORMATTER.format(priceDobule);
	}
}
