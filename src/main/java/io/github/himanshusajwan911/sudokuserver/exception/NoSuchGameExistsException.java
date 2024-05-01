package io.github.himanshusajwan911.sudokuserver.exception;

public class NoSuchGameExistsException extends RuntimeException {

	private static final long serialVersionUID = 1126579521839592937L;

	public NoSuchGameExistsException() {

	}

	public NoSuchGameExistsException(String message) {
		super(message);
	}

	public NoSuchGameExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchGameExistsException(Throwable cause) {
		super(cause);
	}

}
