package com.ibcs.desco.acl.controller;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.ibcs.desco.acl.dao.AclManipulation;
import com.ibcs.desco.acl.model.ACL;
import com.ibcs.desco.acl.model.AclUIHolder;
import com.ibcs.desco.acl.security.Permission;
import com.ibcs.desco.acl.service.DBCon;
/**
*
* @author Ahasanul Ashid, IBCS
* @author Abu Taleb, IBCS
*/
public class StaticResourceHelper {

	private static Connection con;

	private static AclManipulation ac = null;	
	private static List<AclUIHolder> acl = null;
	private static ACL acx = null;

	public static Map<String, Permission> getLoadPermission() {
		return StaticResourceHelper.getAc().LoadPermission();
	}

	
	public static List<String> getMenuSerial() {
		return StaticResourceHelper.getAcx().returnMenu();
	}

	
	public static AclManipulation getAc() {
		if (StaticResourceHelper.ac == null) {
			StaticResourceHelper.ac = new AclManipulation();
		}

		return ac;

	}

	public static List<AclUIHolder> getAcl() {
		StaticResourceHelper.acl = StaticResourceHelper.ac.getAclUI();
		return StaticResourceHelper.acl;
	}

	public static ACL getAcx() {
		if (StaticResourceHelper.acx == null) {

			StaticResourceHelper.acx = new ACL();
		}

		return acx;
	}

	public static Connection getCon() {

		if (con == null) {

			DBCon db = new DBCon();

			try {
				con = db.getJDBCConnection();
			}

			catch (Exception e) {

			}
		}

		return con;
	}

}
