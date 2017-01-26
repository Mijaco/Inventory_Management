package com.ibcs.desco.admin.dao;

import java.util.List;

import com.ibcs.desco.admin.model.AuthUser;

public interface UserDao {
	// data access for add new AuthUser
	public void addAuthUser(AuthUser authUser);

	// data access for get all AuthUser as List
	public List<AuthUser> listAuthUsers();
	
	// data access for get specific one AuthUser information and update AuthUser info
	public AuthUser getAuthUser(int id);
	
	// data access for Delete an AuthUser
	public void deleteAuthUser(AuthUser authUser);	
	
	public List<AuthUser> listAuthUsersByRoleId(int roleId);
	
	public AuthUser getAuthUserByUserId(String userId);
}
