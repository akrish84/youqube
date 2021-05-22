package app.exceptions;

public class ObjectExistsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1504560382205156939L;

	public ObjectExistsException(String message) {
		super(message);
	}
}
