package com.swaglabs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private Properties configProperties;

	public Configuration() throws IOException {
		loadProperties();
	}

	private void loadProperties() throws IOException {
		File src = new File("src\\test\\resources\\config.properties");
		FileInputStream fis = new FileInputStream(src);
		configProperties = new Properties();
		configProperties.load(fis);
	}

	public String getUrl() {
		return configProperties.getProperty("url");
	}

}
