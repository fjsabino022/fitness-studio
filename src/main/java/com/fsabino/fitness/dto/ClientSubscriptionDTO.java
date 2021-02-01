package com.fsabino.fitness.dto;

import org.joda.time.DateTime;

public class ClientSubscriptionDTO {

	private String code;
	private DateTime startDate;
	private DateTime endDate;
	private SubscriptionDTO subscription;
	private ProductDTO product;
	private ClientDTO client;
	private ClientSubscriptionStatusDTO clientSubscriptionStatus;
	
	public String getCode() {
		return code;
	}
	public DateTime getStartDate() {
		return startDate;
	}
	public DateTime getEndDate() {
		return endDate;
	}
	public SubscriptionDTO getSubscription() {
		return subscription;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public ClientDTO getClient() {
		return client;
	}
	public ClientSubscriptionStatusDTO getClientSubscriptionStatus() {
		return clientSubscriptionStatus;
	}
	
	private ClientSubscriptionDTO(String code, SubscriptionDTO subscription, ClientDTO client, DateTime startDate, 
			DateTime endDate, ClientSubscriptionStatusDTO clientSubscriptionStatus, ProductDTO product) {
		this.code = code;
		this.subscription = subscription;
		this.client = client;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clientSubscriptionStatus = clientSubscriptionStatus;
		this.product = product;
	}
	
	public static ClientSubscriptionDTOBuilder builder() {
		return new ClientSubscriptionDTOBuilder();
	}
	
	public static class ClientSubscriptionDTOBuilder {
		private SubscriptionDTO subscription;
		private ClientDTO client;
		private DateTime startDate;
		private DateTime endDate;
		private String code;
		private ProductDTO product;
		private ClientSubscriptionStatusDTO clientSubscriptionStatus;
		
		public ClientSubscriptionDTOBuilder code(String code) {
			this.code = code;
			return this;
		}
		public ClientSubscriptionDTOBuilder client(ClientDTO client) {
			this.client = client;
			return this;
		}
		public ClientSubscriptionDTOBuilder subscription(SubscriptionDTO subscription) {
			this.subscription = subscription;
			return this;
		}
		public ClientSubscriptionDTOBuilder product(ProductDTO product) {
			this.product = product;
			return this;
		}
		public ClientSubscriptionDTOBuilder startDate(DateTime startDate) {
			this.startDate = startDate;
			return this;
		}
		public ClientSubscriptionDTOBuilder endDate(DateTime endDate) {
			this.endDate = endDate;
			return this;
		}
		public ClientSubscriptionDTOBuilder clientSubscriptionStatus(ClientSubscriptionStatusDTO clientSubscriptionStatus) {
			this.clientSubscriptionStatus = clientSubscriptionStatus;
			return this;
		}
		public ClientSubscriptionDTO build() {
			return new ClientSubscriptionDTO(code, subscription, client, startDate, endDate, clientSubscriptionStatus, product);
		}
	}
}
