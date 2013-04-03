package fr.joakimribier.checkhttpapp.services;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

import fr.joakimribier.checkhttpapp.exceptions.SendMailServiceException;

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
@Singleton
public class SendMail implements SendMailService {

	private final static Logger Logger = LoggerFactory.getLogger("application");
	
	public SendMail() {}

	@Override
	public void send(String from, String to, String subject, String text,
			String smtp, String user, String password, boolean debug) throws SendMailServiceException {
		
		try {
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", smtp);
			props.put("mail.smtp.auth", "true");
			 
			Authenticator auth = new SMTPAuthenticator(user, password);
			Session session = Session.getDefaultInstance(props, auth);
			session.setDebug(debug);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text);
			message.setHeader("X-Mailer", "Joakim Ribier (http://www.joakim-ribier.fr)");
			message.setSentDate(new Date());
			
            Transport transport = session.getTransport();
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            Logger.info("send warning mail to {} ", to);
		} catch (AddressException e) {
			throw new SendMailServiceException(e.getMessage(), e);
		} catch (MessagingException e) {
			throw new SendMailServiceException(e.getMessage(), e);
		}
	}
	
	private class SMTPAuthenticator extends Authenticator {
		private final String smtpAuthUser;
		private final String smtpAuthPassword;

		public SMTPAuthenticator(String user, String password) {
			this.smtpAuthUser = user;
			this.smtpAuthPassword = password;
		}

		public PasswordAuthentication getPasswordAuthentication() {
           return new PasswordAuthentication(smtpAuthUser, smtpAuthPassword);
        }
    }
}
