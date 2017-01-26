package com.ibcs.desco.admin.dao;

import java.util.List;

import com.ibcs.desco.admin.model.PermissionTable;

public interface PermissionDao {
	// data access for add new Roles
		public void addPermission(PermissionTable permission);

		// data access for get all Permission as List
		public List<PermissionTable> listPermissions();

		// data access for get specific one Permission information and update Permission info
		public PermissionTable getPermissions(int id);

		// data access for Delete an Permission
		public void deletePermission(PermissionTable permission);
		
		public List<PermissionTable> listPermissionByRoleId(int roleId);
}
