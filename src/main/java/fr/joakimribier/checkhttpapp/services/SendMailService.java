package fr.joakimribier.checkhttpapp.services;

import fr.joakimribier.checkhttpapp.exceptions.SendMailServiceException;

public interface SendMailService {

	void send(String from, String to, String subject, String text, String smtp,
			String user, String password, boolean debug) throws SendMailServiceException;
}
