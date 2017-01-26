package com.ibcs.desco.admin.dao;

import java.util.List;

import com.ibcs.desco.admin.model.Roles;

public interface RoleDao {
	// data access for add new Roles
	public void addRoles(Roles roles);

	// data access for get all Roles as List
	public List<Roles> listRoles();

	// data access for get specific one Roles information and update Roles info
	public Roles getRoles(int id);

	// data access for Delete an Roles
	public void deleteRoles(Roles roles);
}
