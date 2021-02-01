package com.fsabino.fitness.persistence;

public class Client {

	/**
	 * Map to the database
	 */
	private Long id;
	
	private String identification;
	private String firstName;
	private String lastName;
	
	public Long getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getIdentification() {
		return identification;
	}
	
	private Client(Long id, String firstName, String lastName, String identification) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.identification = identification;
	}
	
	public static ClientBuilder builder() {
		return new ClientBuilder();
	}
	
	public static class ClientBuilder {
		private Long id;
		private String firstName;
		private String lastName;
		private String identification;

		public ClientBuilder id(Long id) {
			this.id = id;
			return this;
		}
		public ClientBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public ClientBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public ClientBuilder identification(String identification) {
			this.identification = identification;
			return this;
		}
		
		public Client build() {
			return new Client(id, firstName, lastName, identification);
		}
	}
}
