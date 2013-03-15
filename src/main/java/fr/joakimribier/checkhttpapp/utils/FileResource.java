package fr.joakimribier.checkhttpapp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.google.inject.Singleton;

import fr.joakimribier.checkhttpapp.exceptions.FileResourceServiceException;

@Singleton
public class FileResource implements FileResourceService {

	public FileResource() {}

	@Override
	public String getContent(String filename) throws FileResourceServiceException {
		if (Strings.isNullOrEmpty(filename)) {
			throw new IllegalArgumentException("filename argument is required.");
		}

		try {
			URL url = Resources.getResource(filename);
			return Resources.toString(url, Charsets.UTF_8);
		} catch (IOException e) {
			throw new FileResourceServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public Properties load(String filename) throws FileResourceServiceException {
		if (Strings.isNullOrEmpty(filename)) {
			throw new IllegalArgumentException("filename argument is required.");
		}
		
		FileInputStream in = null;
		try {
			Properties properties = new Properties();
			in = new FileInputStream(filename);
			properties.load(in);
			in.close();
			return properties;
		 } catch (IOException e) {
			 if (in != null) {
				 try {
					in.close();
				} catch (IOException e1) {
					// -- maybe already closed
				}
			 }
			 throw new FileResourceServiceException(e.getMessage(), e);
		 }
	}
}
