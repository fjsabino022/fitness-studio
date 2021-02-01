package com.fsabino.fitness.exception;

public class IlegalProductCodeException extends Exception {

	private static final long serialVersionUID = 1L;

	public IlegalProductCodeException(String message) {
		super(message);
	}
	
	public IlegalProductCodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
