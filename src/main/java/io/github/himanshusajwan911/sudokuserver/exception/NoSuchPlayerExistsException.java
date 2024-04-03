package io.github.himanshusajwan911.sudokuserver.exception;

public class NoSuchPlayerExistsException extends RuntimeException {

	private static final long serialVersionUID = 9101209391764575917L;

	public NoSuchPlayerExistsException() {

	}

	public NoSuchPlayerExistsException(String message) {
		super(message);
	}

	public NoSuchPlayerExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchPlayerExistsException(Throwable cause) {
		super(cause);
	}

}
