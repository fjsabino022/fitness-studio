package com.fsabino.fitness.dto;

import org.joda.time.DateTime;

public class ClientSubscriptionStatusDTO {

	private String status;
	private DateTime startDate;
	
	public String getStatus() {
		return status;
	}
	public DateTime getStartDate() {
		return startDate;
	}
	
	private ClientSubscriptionStatusDTO(String status, DateTime startDate) {
		this.status = status;
		this.startDate = startDate;
	}
	
	public static ClientSubscriptionStatusDTOBuilder builder() {
		return new ClientSubscriptionStatusDTOBuilder();
	}
	
	public static class ClientSubscriptionStatusDTOBuilder {
		private String status;
		private DateTime startDate;
		
		public ClientSubscriptionStatusDTOBuilder startDate(DateTime startDate) {
			this.startDate = startDate;
			return this;
		}
		public ClientSubscriptionStatusDTOBuilder status(String status) {
			this.status = status;
			return this;
		}
		public ClientSubscriptionStatusDTO build() {
			return new ClientSubscriptionStatusDTO(status, startDate);
		}
	}
}
