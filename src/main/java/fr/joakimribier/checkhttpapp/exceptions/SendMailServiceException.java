package fr.joakimribier.checkhttpapp.exceptions;

public class SendMailServiceException extends Exception {
	
	public SendMailServiceException() {
		super();
	}
	
	public SendMailServiceException(String arg0) {
		super(arg0);
	}
	
	public SendMailServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
