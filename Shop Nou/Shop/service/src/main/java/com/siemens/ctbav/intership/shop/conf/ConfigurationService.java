package com.siemens.ctbav.intership.shop.conf;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class ConfigurationService {
	private Properties properties;
	private String host;

	@PostConstruct
	private void initialize() {
		properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/conf.properties"));
			setProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setProperties() {
		host = properties.getProperty("host");
	}

	public String getHost() {
		return this.host;
	}

}
