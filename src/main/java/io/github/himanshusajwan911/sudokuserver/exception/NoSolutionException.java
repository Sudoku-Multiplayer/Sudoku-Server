package io.github.himanshusajwan911.sudokuserver.exception;

public class NoSolutionException extends RuntimeException{

	private static final long serialVersionUID = -5649794074981646639L;

	public NoSolutionException() {
		
	}
	
	public NoSolutionException(String message) {
		super(message);
	}
	
	public NoSolutionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NoSolutionException(Throwable cause) {
		super(cause);
	}
	
}
