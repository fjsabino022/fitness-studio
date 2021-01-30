package com.fsabino.fitness.dto;


public class SubscriptionDTO extends BaseDTO {

	private String code;
	private String description;
	private String type;
	private double price;
	private double tax;
	private String priceTotal;
	private String fee;
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getType() {
		return type;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getTax() {
		return tax;
	}
	
	public String getFee() {
		return fee;
	}
	
	public String getPriceTotal() {
		return priceTotal;
	}

	private SubscriptionDTO(String message) {
		super(message);
	}
	
	private SubscriptionDTO(String code, String description, String type,  double price, double tax, String pricePerMonth, String priceTotal) {
		super();
		this.code = code;
		this.description = description;
		this.type = type;
		this.price = price;
		this.tax = tax;
		this.fee = pricePerMonth;
		this.priceTotal = priceTotal;
	}
	
	public static SubscriptionDTOBuilder builder() {
		return new SubscriptionDTOBuilder();
	}
	
	public static class SubscriptionDTOBuilder {
		private String code;
		private String description;
		private String type;
		private double price;
		private double tax;
		private String fee;
		private String priceTotal;
		private String message;
		
		public SubscriptionDTOBuilder code(String code) {
			this.code = code;
			return this;
		}
		public SubscriptionDTOBuilder description(String description) {
			this.description = description;
			return this;
		}
		public SubscriptionDTOBuilder type(String type) {
			this.type = type;
			return this;
		}
		public SubscriptionDTOBuilder price(double price) {
			this.price = price;
			return this;
		}
		public SubscriptionDTOBuilder tax(double tax) {
			this.tax = tax;
			return this;
		}
		public SubscriptionDTOBuilder fee(String fee) {
			this.fee = fee;
			return this;
		}
		public SubscriptionDTOBuilder priceTotal(String priceTotal) {
			this.priceTotal = priceTotal;
			return this;
		}
		public SubscriptionDTOBuilder message(String message) {
			this.message = message;
			return this;
		}
		public SubscriptionDTO build() {
			return new SubscriptionDTO(code, description, type, price, tax, fee, priceTotal);
		}
		public SubscriptionDTO buildFailedResponse() {
			return new SubscriptionDTO(message);
		}
	}
}
