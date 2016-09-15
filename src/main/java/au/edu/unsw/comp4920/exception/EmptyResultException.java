package au.edu.unsw.comp4920.exception;

public class EmptyResultException extends Exception {

	private static final long serialVersionUID = 1;
	
	public EmptyResultException(String message) {
		super(message);
	}

	public EmptyResultException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
