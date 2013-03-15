package fr.joakimribier.checkhttpapp.utils;

import java.util.Properties;

import fr.joakimribier.checkhttpapp.exceptions.FileResourceServiceException;

public interface FileResourceService {

	String getContent(String file) throws FileResourceServiceException;

	Properties load(String filename) throws FileResourceServiceException;
}
