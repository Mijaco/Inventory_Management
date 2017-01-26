package com.ibcs.desco.admin.service;

import java.util.List;

import com.ibcs.desco.admin.model.PermissionTable;

public interface PermissionService {
	// service for add new PermissionTable
	public void addPermissionTable(PermissionTable permission);

	// service for get all PermissionTable as List
	public List<PermissionTable> listPermissionTable();

	// service for get one PermissionTable and update PermissionTable info by
	// PermissionTable id
	public PermissionTable getPermissionTable(int id);

	// delete an PermissionTable
	public void deletePermissionTable(PermissionTable permission);
	
	public List<PermissionTable> listPermissionByRoleId(int roleId);
}
