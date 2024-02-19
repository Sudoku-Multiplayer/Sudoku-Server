package io.github.himanshusajwan911.sudokuserver.exception;

public class GameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 6546125807527612187L;

	public GameAlreadyExistsException() {

	}

	public GameAlreadyExistsException(String message) {
		super(message);
	}

	public GameAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
