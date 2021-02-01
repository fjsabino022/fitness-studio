package com.fsabino.fitness.dto;

import com.google.common.annotations.VisibleForTesting;

public class ClientSubscriptionCodeDTO extends BaseDTO {

	private Long code;

	public Long getCode() {
		return code;
	}
	
	@VisibleForTesting
	public ClientSubscriptionCodeDTO() {
	}
	
	private ClientSubscriptionCodeDTO(long code) {
		super();
		this.code = code;
	}
	
	private ClientSubscriptionCodeDTO(String message) {
		super(message);
	}
	
	public static ClientSubscriptionCodeDTOBuilder builder() {
		return new ClientSubscriptionCodeDTOBuilder();
	}
	
	public static class ClientSubscriptionCodeDTOBuilder {
		private Long code;
		private String message;

		public ClientSubscriptionCodeDTOBuilder code(Long code) {
			this.code = code;
			return this;
		}
		public ClientSubscriptionCodeDTOBuilder message(String message) {
			this.message = message;
			return this;
		}
		public ClientSubscriptionCodeDTO build() {
			return new ClientSubscriptionCodeDTO(code);
		}
		
		public ClientSubscriptionCodeDTO buildFailedResponse() {
			return new ClientSubscriptionCodeDTO(message);
		}
	}
}
