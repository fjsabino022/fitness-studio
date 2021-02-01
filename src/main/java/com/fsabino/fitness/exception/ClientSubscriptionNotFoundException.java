package com.fsabino.fitness.exception;

public class ClientSubscriptionNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClientSubscriptionNotFoundException(String message) {
		super(message);
	}
	
	public ClientSubscriptionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
