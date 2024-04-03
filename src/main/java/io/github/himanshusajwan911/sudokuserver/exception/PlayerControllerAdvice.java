package io.github.himanshusajwan911.sudokuserver.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlayerControllerAdvice {

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException ex) {

		if (ex.getErrorCode() == 1062) {
			return new ResponseEntity<>("A Player is already registered with given email.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(PropertyValueException.class)
	public ResponseEntity<String> handlePropertyValueException(PropertyValueException ex) {

		String message = ex.getPropertyName() + " cannot be empty.";

		return new ResponseEntity<>(message, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<String> handlePropertyValueException(InvalidCredentialException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(NoSuchPlayerExistsException.class)
	public ResponseEntity<String> handleNoSuchPlayerExistsException(NoSuchPlayerExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

}
