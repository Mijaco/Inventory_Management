package com.ibcs.desco.acl.dao;
/**
*
* @author Ahasanul Ashid, IBCS
* @author Abu Taleb, IBCS
* 
*/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
/**
*
* @author Ahasanul Ashid, IBCS
* @author Abu Taleb, IBCS
* 
*/
public class StaticProperties {
	public final static String USERNAME;
	public final static String PASSWORD;
	public final static String DRIVER;
	public final static String URL;

	static {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("src/main/resources/database.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		USERNAME = p.getProperty("hibernate.connection.username");
		PASSWORD = p.getProperty("hibernate.connection.password");
		DRIVER = p.getProperty("hibernate.connection.driver_class");
		URL = p.getProperty("hibernate.connection.url");

	}

	public static void main(String[] args) {
		System.out.println(USERNAME + " " + PASSWORD);

	}
}
