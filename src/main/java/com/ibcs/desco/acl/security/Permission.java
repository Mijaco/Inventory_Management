package com.ibcs.desco.acl.security;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */

import java.util.List;
import java.util.Map;

/**
 * Contains a map of objects and their associated allowed actions
 */

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
public class Permission {

	/**
	 * A Map containing a list of objects and their corresponding actions
	 * <p>
	 * String: key name of the object List<String>: a list of permissions
	 */
	private Map<String, List<String>> objects;

	public Map<String, List<String>> getObjects() {
		return objects;
	}

	public void setObjects(Map<String, List<String>> objects) {
		this.objects = objects;
	}

}
