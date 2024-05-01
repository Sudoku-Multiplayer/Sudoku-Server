package io.github.himanshusajwan911.sudokuserver.exception;

public class GameFinishedException extends RuntimeException {

	private static final long serialVersionUID = 5247441105037041380L;

	public GameFinishedException() {

	}

	public GameFinishedException(String message) {
		super(message);
	}

	public GameFinishedException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameFinishedException(Throwable cause) {
		super(cause);
	}

}
