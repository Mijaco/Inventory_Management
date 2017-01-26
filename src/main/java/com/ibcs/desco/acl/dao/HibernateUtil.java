package com.ibcs.desco.acl.dao;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
public class HibernateUtil {

	@Autowired
	private static SessionFactory sessionFactory = null;

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	static {
		try {

			Properties properties = new Properties();
			try {
				ClassLoader classLoader = Thread.currentThread()
						.getContextClassLoader();
				properties.load(classLoader
						.getResourceAsStream("database.properties"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml").addProperties(
					properties);

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

}
