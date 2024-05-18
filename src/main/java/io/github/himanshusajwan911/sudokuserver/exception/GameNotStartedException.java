package io.github.himanshusajwan911.sudokuserver.exception;

public class GameNotStartedException extends RuntimeException {

	private static final long serialVersionUID = -1663772827961513448L;

	public GameNotStartedException() {

	}

	public GameNotStartedException(String message) {
		super(message);
	}

	public GameNotStartedException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameNotStartedException(Throwable cause) {
		super(cause);
	}

}
