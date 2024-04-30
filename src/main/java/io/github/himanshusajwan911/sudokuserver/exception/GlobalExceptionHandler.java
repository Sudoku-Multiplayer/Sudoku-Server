package io.github.himanshusajwan911.sudokuserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		return new ResponseEntity<>("Malformed request body: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingParameterException.class)
	public ResponseEntity<String> handleMissingParameterException(MissingParameterException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}