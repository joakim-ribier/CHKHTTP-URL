package fr.joakimribier.checkhttpapp;

import java.util.Properties;

import fr.joakimribier.checkhttpapp.exceptions.ConfigurationKeyNotFoundException;

public class Configuration {
	
	public static class Builder {
		private Properties properties;

		public Builder() {}
		
		public Builder properties(Properties properties) {
			this.properties = properties;
			return this;
		}
		
		public Configuration build() {
			if (properties == null) {
				throw new IllegalArgumentException("properties field is required");
			}
			
			checkKeysConfigurationFile(
					Configuration.CHECK_URL_FIELD,
					Configuration.SMTP_FIELD,
					Configuration.USERNAME_FIELD,
					Configuration.PASSWORD_FIELD,
					Configuration.FROM_FIELD,
					Configuration.TO_FIELD,
					Configuration.SUBJECT_FIELD,
					Configuration.TEXT_FIELD,
					Configuration.SMTP_DEBUG_FIELD);
			
			return new Configuration(properties);
		}
		
		private void checkKeysConfigurationFile(String... keys) {
			for (String key: keys) {
				if (!properties.containsKey(key)) {
					throw new IllegalArgumentException("key " + key + " is required");
				}
			}
		}
	}
	
	private final static String CHECK_URL_FIELD = "check_url";
	private final static String SMTP_FIELD = "smtp";
	private final static String USERNAME_FIELD = "username";
	private final static String PASSWORD_FIELD = "password";
	private final static String FROM_FIELD = "from";
	private final static String TO_FIELD = "to";
	private final static String SUBJECT_FIELD = "subject";
	private final static String TEXT_FIELD = "text";
	private final static String SMTP_DEBUG_FIELD = "smtp_debug";
	
	private final Properties properties;
	
	private Configuration(Properties properties) {
		this.properties = properties;
	}
	
	private String getStringProperty(String key) throws ConfigurationKeyNotFoundException {
		if (!properties.containsKey(key)) {
			throw new ConfigurationKeyNotFoundException("key " + key + "not found.");
		}
		return properties.getProperty(key);
	}
	
	public String getHttpUrlToCheck() throws ConfigurationKeyNotFoundException {
		return getStringProperty(CHECK_URL_FIELD);
	}
	
	public String getSMTPHost() throws ConfigurationKeyNotFoundException {
		return getStringProperty(SMTP_FIELD);
	}
	
	public boolean getSMTPDebug() throws ConfigurationKeyNotFoundException {
		return Boolean.valueOf(getStringProperty(SMTP_DEBUG_FIELD));
	}
	
	public String getSMTPUser() throws ConfigurationKeyNotFoundException {
		return getStringProperty(USERNAME_FIELD);
	}
	
	public String getSMTPPassword() throws ConfigurationKeyNotFoundException {
		return getStringProperty(PASSWORD_FIELD);
	}
	
	public String getMailFrom() throws ConfigurationKeyNotFoundException {
		return getStringProperty(FROM_FIELD);
	}
	
	public String getMailTo() throws ConfigurationKeyNotFoundException {
		return getStringProperty(TO_FIELD);
	}
	
	public String getMailSubject() throws ConfigurationKeyNotFoundException {
		return getStringProperty(SUBJECT_FIELD);
	}
	
	public String getMaiText() throws ConfigurationKeyNotFoundException {
		return getStringProperty(TEXT_FIELD);
	}
	
	public Properties getProperties() {
		return properties;
	}
}
