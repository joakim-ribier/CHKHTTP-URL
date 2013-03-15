package fr.joakimribier.checkhttpapp.exceptions;

public class ConnectionServiceException extends Exception {
	
	public ConnectionServiceException() {
		super();
	}
	
	public ConnectionServiceException(String arg0) {
		super(arg0);
	}
	
	public ConnectionServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
