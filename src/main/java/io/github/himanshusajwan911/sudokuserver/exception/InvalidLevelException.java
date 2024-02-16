package io.github.himanshusajwan911.sudokuserver.exception;

public class InvalidLevelException extends RuntimeException{

	private static final long serialVersionUID = -1227852231587930904L;

	public InvalidLevelException() {
		
	}
	
	public InvalidLevelException(String message) {
		super(message);
	}
	
	public InvalidLevelException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidLevelException(Throwable cause) {
		super(cause);
	}
	
}
