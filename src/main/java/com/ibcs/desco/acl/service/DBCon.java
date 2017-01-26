package com.ibcs.desco.acl.service;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibcs.desco.acl.dao.StaticProperties;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
public class DBCon {

	/*
	 * public static Connection conn=null; public static ResultSet rset=null;
	 * public static Statement stmt=null;
	 */
	private static Logger logger = Logger.getLogger(DBCon.class);

	// get db connection 
	public Connection getJDBCConnection() throws SQLException,
			ClassNotFoundException, Exception

	{
		// conn.close();

		Connection conn = null;

		logger.info("INTO CLASS DBCon");
		try {

			Class.forName(StaticProperties.DRIVER);
			conn = DriverManager.getConnection(StaticProperties.URL,
					StaticProperties.USERNAME, StaticProperties.PASSWORD);
			// ("jdbc:oracle:thin:@192.168.0.4:1521:db11g", "hr", "hr");
			// ("jdbc:oracle:thin:@localhost:1521:db11g", "hr", "hr");
			// ("jdbc:oracle:thin:@192.168.125.38:1521:orcl", "icx", "icx");
			// ("jdbc:oracle:thin:@192.168.6.15:1531:vtldb", "icx",
			// "bantelicx");
			// ("jdbc:oracle:thin:@192.168.6.15:1531:vtldb", "icx",
			// "voicetelicx");
			// ("jdbc:oracle:thin:@localhost:1521:orcl", "icx",
			// "bantelicx");
			// ("jdbc:oracle:thin:@localhost:1521:orcl", "icx", "icx");
		} // @//machineName:port/SID, userid, password
			// localhost:1521:OCSRPT", "ocsuser", "ocs123");
		catch (Exception ex) {
			logger.fatal(ex);
			throw ex;
		}
		return conn;
	}

}
