package com.ibcs.desco.acl.security;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibcs.desco.acl.dao.AclManipulation;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
public class PermInjection {

	public Permission adminPermission() {
		AclManipulation ac = new AclManipulation();
		ac.LoadPermission();
		Permission p = new Permission();
		Map<String, List<String>> permission = new HashMap<String, List<String>>();
		List<String> l1 = new ArrayList<String>();
		l1.add("READ");
		l1.add("WRITE");
		permission.put("com.icx.Model.Source", l1);

		p.setObjects(permission);

		return p;

	}

	public Permission UserPermission() {
		AclManipulation ac = new AclManipulation();
		ac.LoadPermission();
		Permission p = new Permission();
		Map<String, List<String>> permission = new HashMap<String, List<String>>();
		List<String> l1 = new ArrayList<String>();

		l1.add("READ");
		permission.put("com.icx.Model.Source", l1);

		p.setObjects(permission);

		return p;

	}

}
