package fr.joakimribier.checkhttpapp.exceptions;

public class ConfigurationKeyNotFoundException extends Exception {
	
	public ConfigurationKeyNotFoundException() {
		super();
	}
	
	public ConfigurationKeyNotFoundException(String arg0) {
		super(arg0);
	}
	
	public ConfigurationKeyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
