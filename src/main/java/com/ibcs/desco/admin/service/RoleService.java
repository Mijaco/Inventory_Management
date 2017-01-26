package com.ibcs.desco.admin.service;

import java.util.List;

import com.ibcs.desco.admin.model.Roles;

public interface RoleService {
	// service for add new Roles
	public void addRoles(Roles role);

	// service for get all role as List
	public List<Roles> listRoles();

	// service for get one Roles and update Roles info by Roles id
	public Roles getRoles(int id);

	// delete an role
	public void deleteRoles(Roles role);
}
