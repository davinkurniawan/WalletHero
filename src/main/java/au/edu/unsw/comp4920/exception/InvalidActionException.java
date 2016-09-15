package au.edu.unsw.comp4920.exception;

public class InvalidActionException extends Exception {

	private static final long serialVersionUID = 1;
	
	public InvalidActionException(String message) {
		super(message);
	}

	public InvalidActionException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
