package fr.joakimribier.checkhttpapp.guice;

import com.google.inject.AbstractModule;

import fr.joakimribier.checkhttpapp.services.SendMail;
import fr.joakimribier.checkhttpapp.services.SendMailService;
import fr.joakimribier.checkhttpapp.utils.FileResource;
import fr.joakimribier.checkhttpapp.utils.FileResourceService;

public class CheckHttpAppModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FileResourceService.class).to(FileResource.class);
		bind(SendMailService.class).to(SendMail.class);
	}
}
