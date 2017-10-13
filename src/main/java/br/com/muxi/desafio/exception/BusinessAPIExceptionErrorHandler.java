package br.com.muxi.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessAPIExceptionErrorHandler {

	@ExceptionHandler({BusinessAPIException.class})
	public ResponseEntity<Object> handleBusinessApiException(BusinessAPIException businessException) {		
		return new ResponseEntity<Object>( new Body(businessException.getMessage()) , HttpStatus.BAD_REQUEST);
	}

	private class Body {
		
		
		private String message;
		
		public Body(String message) {
			this.message = message;

		}
		
		public String getMessage(){
			return this.message;
		}
	}
}
