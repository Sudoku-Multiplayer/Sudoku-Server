package io.github.himanshusajwan911.sudokuserver.exception;

public class VoteInitializationException extends RuntimeException {

	private static final long serialVersionUID = 6580991477621819641L;

	public VoteInitializationException() {

	}

	public VoteInitializationException(String message) {
		super(message);
	}

	public VoteInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public VoteInitializationException(Throwable cause) {
		super(cause);
	}

}
