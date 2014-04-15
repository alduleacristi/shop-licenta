package com.siemens.ctbav.intership.recommandation.conf;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class ConfigurationService {
	private Properties properties;
	private String nrOfUsers;
	
	@PostConstruct
	private void initialize(){
		properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/conf.properties"));
			setProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setProperties() {
		nrOfUsers = properties.getProperty("nrOfUsers");
	}
	
	public int getNrOfUsers(){
		try{
			int nr = Integer.parseInt(nrOfUsers);
			return nr;
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 1000;
		}
	}
}
