package fr.rjoakim.app.checklink;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.rjoakim.app.checklink.exceptions.ConfigurationKeyNotFoundException;
import fr.rjoakim.app.checklink.exceptions.ConnectionServiceException;
import fr.rjoakim.app.checklink.exceptions.FileResourceServiceException;
import fr.rjoakim.app.checklink.exceptions.SendMailServiceException;
import fr.rjoakim.app.checklink.guice.CheckHttpAppModule;
import fr.rjoakim.app.checklink.services.SendMailService;
import fr.rjoakim.app.checklink.utils.FileResourceService;

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
public class Main {
	
	private final static Logger Logger = LoggerFactory.getLogger("application");
	private final static String DEFAULT_PATH = "file.path";
	
	public static void main(String[] args) {
		
		final Injector injector = Guice.createInjector(new CheckHttpAppModule());
		final FileResourceService fileResource = injector.getInstance(FileResourceService.class);
		final SendMailService sendMail = injector.getInstance(SendMailService.class);
		
		try {
			final String pathFileConfiguration = getArgsOrDefaultPathConfiguration(fileResource, args);
			if (Strings.isNullOrEmpty(pathFileConfiguration)) {
				Logger.info("path of configuration file is not defined - programme aborted");
				return;
			}
			
			final Properties properties = fileResource.load(pathFileConfiguration);
			final Configuration configuration = new Configuration.Builder().properties(properties).build();
			final Collection<String> URLsHttp = configuration.listURLsHttp();
			for (String URL: URLsHttp) {
				checkAndSendMail(sendMail, configuration, URL);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
    }

	private static void checkAndSendMail(final SendMailService sendMail,
			final Configuration configuration, String URL)
					throws ConnectionServiceException, SendMailServiceException, ConfigurationKeyNotFoundException {
		
		int code = ifConnect(URL);
		if (code != HttpURLConnection.HTTP_OK) {
			Logger.error("check url {} failed (code:{})", new Object[]{ URL, code});
			String subject = buildMailSubject(configuration.getMailSubject(), URL);
			String text = buildMailText(configuration.getMaiText(), URL, code);
			sendMail.send(
					configuration.getMailFrom(),
					configuration.getMailTo(),
					subject,
					text,
					configuration.getSMTPHost(),
					configuration.getSMTPUser(),
					configuration.getSMTPPassword(),
					configuration.getSMTPDebug());
		} else {
			Logger.info("check url {} success", URL);
		}
	}

	private static String getArgsOrDefaultPathConfiguration(FileResourceService fileUtils,
			String[] args) throws FileResourceServiceException {
		
		if (args.length == 1) {
			return args[0];
		} else {
			return fileUtils.getContent(DEFAULT_PATH);
		}
	}
	
	private static int ifConnect(String url) throws ConnectionServiceException {
		try {
			URL myURL = new URL(url);
			HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
			myURLConnection.connect();
			return myURLConnection.getResponseCode();
		} catch (MalformedURLException e) {
			throw new ConnectionServiceException(e.getMessage(), e);
		} catch (IOException e) {
			return HttpURLConnection.HTTP_NOT_FOUND;
		}
	}

	private static String getDateTime() {
		return DateTimeFormat
				.forPattern("d MMMM, yyyy 'à' HH:mm:ss")
				.print(new DateTime());
	}
	
	private static String buildMailSubject(String subject, String URL) {
		return subject.replace("{0}", URL);
	}

	private static String buildMailText(String text, String URL, int code) {
		return text.replace("{0}", getDateTime()).
				replace("{1}", URL).
				replace("{2}", String.valueOf(code));
	}
}
