package com.getgifted.demo.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * The Class GlobalExceptionsHandler.
 */
@ControllerAdvice
public class GlobalExceptionsHandler {
	
	/**
	 * Handle resource not found exception.
	 *
	 * @param exception the exception
	 * @param request the request
	 * @return the response entity
	 */
	//Handle specific exceptions
		@ExceptionHandler(ResourceNotFoundException.class)
		public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException
				exception, WebRequest request){
			ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
			return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
			
		}
		
		/**
		 * Handle resource already exists exception.
		 *
		 * @param exception the exception
		 * @param request the request
		 * @return the response entity
		 */
		//Handle specific exceptions
		@ExceptionHandler(value = ResourceAlreadyFoundException.class)
	    public ResponseEntity<?> handleResourceAlreadyExistsException(ResourceAlreadyFoundException 
	    		exception, WebRequest request) {
			ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
	        return new ResponseEntity<Object>(errorDetails, HttpStatus.ALREADY_REPORTED);
	    }
		
		/**
		 * Handle global exception.
		 *
		 * @param exception the exception
		 * @param request the request
		 * @return the response entity
		 */
		//Handle global exceptions
		@ExceptionHandler(Exception.class)
		public ResponseEntity<?> handleGlobalException(ResourceNotFoundException
				exception, WebRequest request){
			ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
			return new ResponseEntity<Object>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

}
