package fr.rjoakim.app.checklink;

import com.google.common.collect.Lists;
import fr.rjoakim.app.checklink.exceptions.ConfigurationKeyNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * 
 * Copyright 2013 Joakim Ribier
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
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
					Configuration.URL_FIELD + 1,
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
	
	private final static String URL_FIELD = "URL_";
	private final static String SMTP_FIELD = "smtp";
	private final static String USERNAME_FIELD = "username";
	private final static String PASSWORD_FIELD = "password";
	private final static String FROM_FIELD = "from";
	private final static String TO_FIELD = "to";
	private final static String CC_FIELD = "cc";
	private final static String SUBJECT_FIELD = "subject";
	private final static String TEXT_FIELD = "text";
	private final static String SMTP_DEBUG_FIELD = "smtp_debug";
	
	private final Properties properties;
	
	private Configuration(Properties properties) {
		this.properties = properties;
	}
	
	private String getPropertyValue(String key) throws ConfigurationKeyNotFoundException {
		if (!properties.containsKey(key)) {
			throw new ConfigurationKeyNotFoundException("key " + key + "not found");
		}
		return properties.getProperty(key);
	}

	private String getPropertyValue(String key, String defaultValue) {
		try {
			return getPropertyValue(key);
		} catch (ConfigurationKeyNotFoundException e) {
			return defaultValue;
		}
	}
	
	public Collection<String> listURLsHttp() {
		List<String> URLs = Lists.newArrayList();
		fillURLs(1, URLs);
		return URLs;
	}
	
	private void fillURLs(int id, List<String> URLs) {
		try {
			String URL = getPropertyValue(URL_FIELD + id);
			if (URL != null) {
				URLs.add(URL);
				fillURLs(id + 1, URLs);
			}
		} catch (ConfigurationKeyNotFoundException e) {
			return ;
		}
	}
	
	
	public String getSMTPHost() throws ConfigurationKeyNotFoundException {
		return getPropertyValue(SMTP_FIELD);
	}
	
	public boolean getSMTPDebug() throws ConfigurationKeyNotFoundException {
		return Boolean.valueOf(getPropertyValue(SMTP_DEBUG_FIELD));
	}
	
	public String getSMTPUser() throws ConfigurationKeyNotFoundException {
		return getPropertyValue(USERNAME_FIELD);
	}
	
	public String getSMTPPassword() throws ConfigurationKeyNotFoundException {
		return getPropertyValue(PASSWORD_FIELD);
	}
	
	public String getMailFrom() throws ConfigurationKeyNotFoundException {
		return getPropertyValue(FROM_FIELD);
	}
	
	public String getMailTo() throws ConfigurationKeyNotFoundException {
		return getPropertyValue(TO_FIELD);
	}

	public String getMailCC() {
		return getPropertyValue(CC_FIELD, "");
	}
	
	public String getMailSubject() throws ConfigurationKeyNotFoundException {
		return getPropertyValue(SUBJECT_FIELD);
	}
	
	public String getMaiText() throws ConfigurationKeyNotFoundException {
		return getPropertyValue(TEXT_FIELD);
	}
	
	public Properties getProperties() {
		return properties;
	}
}
