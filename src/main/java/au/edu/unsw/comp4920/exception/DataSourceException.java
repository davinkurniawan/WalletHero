package au.edu.unsw.comp4920.exception;

public class DataSourceException extends Exception {
	
	private static final long serialVersionUID = 1;
	
	public DataSourceException(String message) {
		super(message);
	}

	public DataSourceException(String message, Throwable cause) {
		super(message, cause);
	}

}
