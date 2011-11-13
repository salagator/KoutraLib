package com.koutra.koutralib.domain;

public class DeltaApplicationException extends Exception {

	private static final long serialVersionUID = -4351121675430151023L;

	public DeltaApplicationException(String message) {
		super(message);
	}
	
	public DeltaApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}
