package com.fsabino.fitness.persistence;

import java.util.List;

public class Product {

	private Long id;
	private String code;
	private String name;
	private String description;
	private List<Subscription> subscriptions;
	
	public Long getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	private Product(Long id, String code, String name, String description, List<Subscription> subscriptions) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.subscriptions = subscriptions;
	}
	
	public static ProductBuilder builder() {
		return new ProductBuilder();
	}
	
	public static class ProductBuilder {
		private Long id;
		private String code;
		private String name;
		private String description;
		private List<Subscription> subscriptions;
		
		public ProductBuilder id(Long id) {
			this.id = id;
			return this;
		}
		public ProductBuilder code(String code) {
			this.code = code;
			return this;
		}
		public ProductBuilder name(String name) {
			this.name = name;
			return this;
		}
		public ProductBuilder description(String description) {
			this.description = description;
			return this;
		}
		public ProductBuilder subscriptions(List<Subscription> subscriptions) {
			this.subscriptions = subscriptions;
			return this;
		}
		public Product build() {
			return new Product(id, code, name, description, subscriptions);
		}
	}
}
