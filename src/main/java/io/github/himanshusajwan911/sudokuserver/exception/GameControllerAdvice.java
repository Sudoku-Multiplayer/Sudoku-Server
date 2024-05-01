package io.github.himanshusajwan911.sudokuserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GameControllerAdvice {

	@ExceptionHandler(NoSuchGameExistsException.class)
	public ResponseEntity<String> handleNoSuchGameExistsException(NoSuchGameExistsException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(GameFinishedException.class)
	public ResponseEntity<String> handleGameFinishedException(GameFinishedException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

}
