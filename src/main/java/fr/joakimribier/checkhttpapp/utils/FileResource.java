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
