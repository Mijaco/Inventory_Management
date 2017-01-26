package com.ibcs.desco.acl.dao;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibcs.desco.acl.model.AclUIHolder;
import com.ibcs.desco.acl.model.ObjectReference;
import com.ibcs.desco.acl.model.PermissionTable;
import com.ibcs.desco.acl.model.Roles;
import com.ibcs.desco.acl.security.Permission;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
public class AclManipulation {

	public static Boolean session_info = false;
	private DBAction db = null;

	public AclManipulation() {
		this.db = new DBAction();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> viewAll(Class cls) {
		// db.calSP();
		List l = db.getAll(cls);
		return l;

	}

	public void add(Object o) {

		db.add(o);

	}

	public void merge(Object o) {

		db.update(o);
	}

	@SuppressWarnings("rawtypes")
	public Object getbyId(int id, Class cls) throws ClassNotFoundException {
		Object s = db.getById(id, cls);
		return s;
	}

	@SuppressWarnings("rawtypes")
	public void delete(int id, Class cls) throws ClassNotFoundException {

		db.delete(id, cls);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> viewBySource(Class cls, String id) {

		List l = db.search(id, cls);
		return l;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object> LogIn(String pass, String id) {

		List l = db.Login(id, pass);
		return l;
	}

	@SuppressWarnings("rawtypes")
	public List getRoles() {
		List l = db.getAll(Roles.class);
		return l;

	}

	@SuppressWarnings("rawtypes")
	public List GetObjects() {
		List l = db.getAll(ObjectReference.class);
		return l;
	}

	public List<PermissionTable> getPermission(int role_id, int obj_id) {
		List<PermissionTable> l = db.getPermission(role_id, obj_id);
		return l;

	}

	// Map<String,List<String>>
	@SuppressWarnings({ "unchecked", "unused" })
	public Map<String, Permission> LoadPermission() {
		Map<String, Permission> PermissionHub = new HashMap<String, Permission>();
		List<ObjectReference> objects = this.GetObjects();
		Permission permPut = new Permission();
		Map<String, List<String>> permissionMap = new HashMap<String, List<String>>();
		String currentRole = "";
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// auth.getAuthorities()
			Collection<? extends GrantedAuthority> auths = auth
					.getAuthorities();
			for (GrantedAuthority a : auths) {
				currentRole = a.getAuthority();
				break;
			}
		}

		Roles roleObject = db.getRoleObjectByRoleName(currentRole);
		int currentRoleId = roleObject.getRole_id();
		for (int j = 0; j < objects.size(); j++) {
			String CurrentObject = objects.get(j).getClass_name();
			int CurrentObjectId = objects.get(j).getId();
			String CurrentObjectReference = objects.get(j).getClass_reference();

			List<PermissionTable> permissions = getPermission(currentRoleId,
					CurrentObjectId);

			PermissionTable currentPermission = new PermissionTable();
			if (!permissions.isEmpty()) {
				currentPermission = permissions.get(0);
				permissionMap.put(CurrentObjectReference,
						getValues(currentPermission));

			}

		}
		permPut.setObjects(permissionMap);
		PermissionHub.put(currentRole, permPut);

		//

		/*
		 * for (int i = 0; i < role.size(); i++) { Permission permPut = new
		 * Permission(); Map<String, List<String>> permissionMap = new
		 * HashMap<String, List<String>>(); String currentRole =
		 * role.get(i).getRole(); int currentRoleId = role.get(i).getRole_id();
		 * // ListOfPermissions.clear();
		 * 
		 * for (int j = 0; j < objects.size(); j++) { String CurrentObject =
		 * objects.get(j).getClass_name(); int CurrentObjectId =
		 * objects.get(j).getId(); String CurrentObjectReference =
		 * objects.get(j) .getClass_reference();
		 * 
		 * List<PermissionTable> permissions = getPermission( currentRoleId,
		 * CurrentObjectId);
		 * 
		 * PermissionTable currentPermission = new PermissionTable(); //
		 * List<String> ListOfPermissions1=new ArrayList(); if
		 * (!permissions.isEmpty()) { currentPermission = permissions.get(0);
		 * permissionMap.put(CurrentObjectReference,
		 * getValues(currentPermission));
		 * 
		 * } else {
		 * 
		 * // //System.out.print("catch");
		 * 
		 * }
		 * 
		 * } permPut.setObjects(permissionMap); PermissionHub.put(currentRole,
		 * permPut);
		 * 
		 * }
		 */

		return PermissionHub;
	}

	public List<String> getValues(PermissionTable currentPermission) {
		ArrayList<String> ListOfPermissions = new ArrayList<String>();

		if (currentPermission.getP_read() == 1) {
			ListOfPermissions.add("READ");
		}

		if (currentPermission.getP_write() == 1) {

			ListOfPermissions.add("WRITE");

		}

		if (currentPermission.getP_edit() == 1) {

			ListOfPermissions.add("EDIT");

		}

		if (currentPermission.getP_delete() == 1) {

			ListOfPermissions.add("DELETE");

		}

		return ListOfPermissions;
	}

	public String getRoleName(int id) {

		return db.RoleName(id).replace("[", "").replace("]", "");

	}

	public String getObjectName(int id) {

		return db.ObjectName(id).replace("[", "").replace("]", "");

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public List<AclUIHolder> getAclUI() {
		List<AclUIHolder> aclObject = new ArrayList<AclUIHolder>();
		Class cls = PermissionTable.class;

		List<Object> permissionTable = db.getAll(cls);
		// //System.out.println(permissionTable.size()+"acl");
		for (int i = 0; i < permissionTable.size(); i++) {
			AclUIHolder acl = new AclUIHolder();
			PermissionTable pt = (PermissionTable) permissionTable.get(i);
			int id = pt.getP_id();
			int roleId = pt.getRole_id();
			int objectId = pt.getObject_id();
			// //System.out.println(roleId+"><><"+objectId);
			acl.setId(pt.getP_id());
			acl.setRole(getRoleName(roleId));
			acl.setObject(getObjectName(objectId));
			acl.setP_read(pt.getP_read());
			acl.setP_write(pt.getP_write());
			acl.setP_edit(pt.getP_edit());
			acl.setP_delete(pt.getP_delete());
			aclObject.add(acl);
		}

		return aclObject;
	}

}
