package com.ibcs.desco.acl.service;
/**
*
* @author Ahasanul Ashid, IBCS
* @author Abu Taleb, IBCS
* 
*/
public class PermissionInfo {
	String query = "SELECT P_READ P, P_WRITE P, P_EDIT P, P_DELETE P, CLASS_NAME O, ROLE R, USERID AU"
			+ "FROM AUTH_USER AU, ROLES R, OBJECT_REFERENCE O, PERMISSION_TABLE P"
			+ "WHERE AU.ROLEID = R.ROLE_ID"
			+ "AND R.ROLE_ID = P.ROLE_ID" 
			+ "AND P.OBJECT_ID = O.ID"
			+ "AND USERID = 'TEST'";

}
