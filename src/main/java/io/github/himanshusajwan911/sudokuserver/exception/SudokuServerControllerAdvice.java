package io.github.himanshusajwan911.sudokuserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SudokuServerControllerAdvice {

	@ExceptionHandler(InvalidLevelException.class)
	public ResponseEntity<String> handleInvalidLevelException(InvalidLevelException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSolutionException.class)
	public ResponseEntity<String> handleNoSolutionException(NoSolutionException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
	}

	@ExceptionHandler(GameAlreadyExistsException.class)
	public ResponseEntity<String> handleGameAlreadyExistsException(GameAlreadyExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public ResponseEntity<String> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
