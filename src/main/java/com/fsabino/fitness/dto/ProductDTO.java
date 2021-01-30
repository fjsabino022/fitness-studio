package com.fsabino.fitness.dto;

import java.util.List;

public class ProductDTO extends BaseDTO {

	private String code;
	private String name;
	private String description;
	private List<SubscriptionDTO> subscriptions;
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public List<SubscriptionDTO> getSubscriptions() {
		return subscriptions;
	}

	private ProductDTO(String code, String name, String description, List<SubscriptionDTO> subscriptions) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.subscriptions = subscriptions;
	}
	
	private ProductDTO(String message) {
		super(message);
	}
	
	public static ProductDTOBuilder builder() {
		return new ProductDTOBuilder();
	}
	
	public static class ProductDTOBuilder {
		private String code;
		private String name;
		private String description;
		private String message;
		private List<SubscriptionDTO> subscriptions;
		
		public ProductDTOBuilder code(String code) {
			this.code = code;
			return this;
		}
		public ProductDTOBuilder name(String name) {
			this.name = name;
			return this;
		}
		public ProductDTOBuilder description(String description) {
			this.description = description;
			return this;
		}
		public ProductDTOBuilder subscriptions(List<SubscriptionDTO> subscriptions) {
			this.subscriptions = subscriptions;
			return this;
		}
		public ProductDTOBuilder message(String message) {
			this.message = message;
			return this;
		}
		public ProductDTO build() {
			return new ProductDTO(code, name, description, subscriptions);
		}
		
		public ProductDTO buildFailedResponse() {
			return new ProductDTO(message);
		}
	}
}
