package com.siemens.ctbav.intership.shop.util.superadmin;
/*
import java.io.InputStream;

import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;

public class SessionHandler {

	private Repository repository;
	private Session session;

	public SessionHandler(InputStream input, String home)
			throws ConfigurationException {
		repository = new TransientRepository(RepositoryConfig.create(input,
				home));
		session = null;
	}

	public Session login() {
		
		try{
			session = repository.login(new SimpleCredentials("username",
					"password".toCharArray()));
		} catch (LoginException e) {
			System.out.println("LoginException");

		} catch (RepositoryException e) {
			System.out.println("RepositoryException");
		}
		return session;
	}

	public void logout() {
		if (session != null && session.isLive())
			session.logout();
	}

	public Session getSession() {
		return session;
	}
}*/
