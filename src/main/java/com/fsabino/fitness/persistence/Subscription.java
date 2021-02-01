package com.fsabino.fitness.persistence;

public class Subscription {

	public enum Type {
		DAY,
		WEEK,
		MONTH,
		SIX_MONTH,
		YEAR;
	}
	
	/**
	 * Map to the database
	 */
	private Long id;
	
	private String code;
	private String description;
	private Type type;
	private boolean isFree;
	private double price;
	private double tax;
	private Product product;

	public Long getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Type getType() {
		return type;
	}
	
	public boolean getIsFree() {
		return isFree;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getTax() {
		return tax;
	}
	
	public Product getProduct() {
		return product;
	}
	
	private Subscription(Long id, String code, String description, Type type, Boolean isFree, Double price, Double tax,
			Product product) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.type = type;
		this.isFree = isFree;
		this.price = price;
		this.tax = tax;
		this.product = product;
	}
	
	public static SubscriptionBuilder builder() {
		return new SubscriptionBuilder();
	}
	
	public static class SubscriptionBuilder {
		private Long id;
		private String code;
		private String description;
		private Type type;
		private boolean isFree;
		private double price;
		private double tax;
		private Product product;
		
		public SubscriptionBuilder id(Long id) {
			this.id = id;
			return this;
		}
		public SubscriptionBuilder code(String code) {
			this.code = code;
			return this;
		}
		public SubscriptionBuilder description(String description) {
			this.description = description;
			return this;
		}
		public SubscriptionBuilder type(Type type) {
			this.type = type;
			return this;
		}
		public SubscriptionBuilder isFree(boolean isFree) {
			this.isFree = isFree;
			return this;
		}
		public SubscriptionBuilder price(double price) {
			this.price = price;
			return this;
		}
		public SubscriptionBuilder tax(double tax) {
			this.tax = tax;
			return this;
		}
		public SubscriptionBuilder product(Product product) {
			this.product = product;
			return this;
		}
		public Subscription build() {
			return new Subscription(id, code, description, type, isFree, price, tax, product);
		}
	}
}
