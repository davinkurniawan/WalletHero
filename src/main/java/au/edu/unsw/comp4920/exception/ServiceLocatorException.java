package au.edu.unsw.comp4920.exception;

public class ServiceLocatorException extends Exception {

	private static final long serialVersionUID = 1;

	public ServiceLocatorException(String message) {
		super(message);
	}

	public ServiceLocatorException(String message, Throwable cause) {
		super(message, cause);
	}
	
}