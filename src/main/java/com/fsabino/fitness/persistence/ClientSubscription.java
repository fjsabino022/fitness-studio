package com.fsabino.fitness.persistence;

import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;

public class ClientSubscription {
	
	private Long id;
	private String code;
	private Subscription subscription;
	private Client client;
	private DateTime startDate;
	private DateTime endDate;
	private List<ClientSubscriptionStatus> clientSubscriptionStatus;
	
	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	public Subscription getSubscription() {
		return subscription;
	}
	
	public Client getClient() {
		return client;
	}
	
	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}
	
	public List<ClientSubscriptionStatus> getClientSubscriptionStatus() {
		return clientSubscriptionStatus;
	}

	private ClientSubscription(Long id, String code, Subscription subscription, Client client, DateTime startDate, 
			DateTime endDate, List<ClientSubscriptionStatus> clientSubscriptionStatus) {
		this.id = id;
		this.code = code;
		this.subscription = subscription;
		this.client = client;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clientSubscriptionStatus = clientSubscriptionStatus;
	}
	
	public static ClientSubscriptionBuilder builder() {
		return new ClientSubscriptionBuilder();
	}
	
	public static class ClientSubscriptionBuilder {
		private Long id;
		private Subscription subscription;
		private Client client;
		private DateTime startDate;
		private DateTime endDate;
		private String code;
		
		public ClientSubscriptionBuilder id(Long id) {
			this.id = id;
			return this;
		}
		public ClientSubscriptionBuilder code(String code) {
			this.code = code;
			return this;
		}
		public ClientSubscriptionBuilder client(Client client) {
			this.client = client;
			return this;
		}
		public ClientSubscriptionBuilder subscription(Subscription subscription) {
			this.subscription = subscription;
			return this;
		}
		public ClientSubscriptionBuilder startDate(DateTime startDate) {
			this.startDate = startDate;
			return this;
		}
		public ClientSubscriptionBuilder endDate(DateTime endDate) {
			this.endDate = endDate;
			return this;
		}
		public ClientSubscription build() {
			return new ClientSubscription(id, code, subscription, client, startDate, endDate, Lists.newArrayList());
		}
	}
}
