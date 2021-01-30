package com.fsabino.fitness.dto;

public abstract class BaseDTO {

	private String message;

	public BaseDTO() {
	}

	public String getMessage() {
		return message;
	}

	public BaseDTO(String message) {
		this.message = message;
	}
}
