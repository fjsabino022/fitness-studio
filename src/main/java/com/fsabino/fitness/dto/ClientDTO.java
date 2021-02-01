package com.fsabino.fitness.dto;

public class ClientDTO {

	private String identification;
	private String firstName;
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getIdentification() {
		return identification;
	}
	
	private ClientDTO(String firstName, String lastName, String identification) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.identification = identification;
	}
	
	public static ClientDTOBuilder builder() {
		return new ClientDTOBuilder();
	}
	
	public static class ClientDTOBuilder {
		private String firstName;
		private String lastName;
		private String identification;

		public ClientDTOBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public ClientDTOBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public ClientDTOBuilder identification(String identification) {
			this.identification = identification;
			return this;
		}
		
		public ClientDTO build() {
			return new ClientDTO(firstName, lastName, identification);
		}
	}
}
