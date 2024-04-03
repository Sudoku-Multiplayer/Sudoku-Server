package io.github.himanshusajwan911.sudokuserver.exception;

public class InvalidCredentialException extends RuntimeException {

	private static final long serialVersionUID = -2358243500163047754L;

	public InvalidCredentialException() {

	}

	public InvalidCredentialException(String message) {
		super(message);
	}

	public InvalidCredentialException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCredentialException(Throwable cause) {
		super(cause);
	}

}
