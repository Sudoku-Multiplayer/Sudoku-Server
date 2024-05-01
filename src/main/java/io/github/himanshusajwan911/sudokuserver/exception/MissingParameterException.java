package io.github.himanshusajwan911.sudokuserver.exception;

public class MissingParameterException extends RuntimeException {

	private static final long serialVersionUID = 4341914994204466792L;

	public MissingParameterException() {

	}

	public MissingParameterException(String message) {
		super(message);
	}

	public MissingParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingParameterException(Throwable cause) {
		super(cause);
	}

}
