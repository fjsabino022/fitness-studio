package com.fsabino.fitness.exception;

public class SubscriptionNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public SubscriptionNotFoundException(String message) {
		super(message);
	}
	
	public SubscriptionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
