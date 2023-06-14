package com.getgifted.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ResourceAlreadyFoundException.
 */
@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class ResourceAlreadyFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6041047929492569224L;
	
	/**
	 * Instantiates a new resource already found exception.
	 *
	 * @param message the message
	 */
	public ResourceAlreadyFoundException(String message) {
        super(message);
    }

}
