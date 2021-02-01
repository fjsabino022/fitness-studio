package com.fsabino.fitness.exception;

public class IlegalSubscriptionCodeException extends Exception {

	private static final long serialVersionUID = 1L;

	public IlegalSubscriptionCodeException(String message) {
		super(message);
	}
	
	public IlegalSubscriptionCodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
