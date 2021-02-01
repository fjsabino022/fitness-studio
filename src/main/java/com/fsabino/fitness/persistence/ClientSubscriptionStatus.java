package com.fsabino.fitness.persistence;

import org.joda.time.DateTime;

public class ClientSubscriptionStatus {

	public enum Status {
		ACTIVE,
		CANCELLED,
		PAUSED;
	}
	
	private Long id; 
	private Status status;
	private ClientSubscription clientSubscription;
	private DateTime startDate;
	private DateTime endDate;
	
	public Long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ClientSubscription getClientSubscription() {
		return clientSubscription;
	}

	public void setClientSubscription(ClientSubscription clientSubscription) {
		this.clientSubscription = clientSubscription;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	private ClientSubscriptionStatus(Long id, Status status, ClientSubscription clientSubscription, DateTime startDate,
			DateTime endDate) {
		this.id = id;
		this.status = status;
		this.clientSubscription = clientSubscription;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public static ClientSubscriptionStatusBuilder builder() {
		return new ClientSubscriptionStatusBuilder();
	}
	
	public static class ClientSubscriptionStatusBuilder {
		private Long id; 
		private Status status;
		private ClientSubscription clientSubscription;
		private DateTime startDate;
		private DateTime endDate;
		
		public ClientSubscriptionStatusBuilder id(Long id) {
			this.id = id;
			return this;
		}
		public ClientSubscriptionStatusBuilder startDate(DateTime startDate) {
			this.startDate = startDate;
			return this;
		}
		public ClientSubscriptionStatusBuilder endDate(DateTime endDate) {
			this.endDate = endDate;
			return this;
		}
		public ClientSubscriptionStatusBuilder status(Status status) {
			this.status = status;
			return this;
		}
		public ClientSubscriptionStatusBuilder clientSubscription(ClientSubscription clientSubscription) {
			this.clientSubscription = clientSubscription;
			return this;
		}
		public ClientSubscriptionStatus build() {
			return new ClientSubscriptionStatus(id, status, clientSubscription, startDate, endDate);
		}
	}
}
