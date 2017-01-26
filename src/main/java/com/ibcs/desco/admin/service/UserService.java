package com.ibcs.desco.admin.service;

import java.util.List;

import com.ibcs.desco.admin.model.AuthUser;

public interface UserService {
	// service for add new authUser
	public void addAuthUser(AuthUser authUser);

	// service for get all authUser as List
	public List<AuthUser> listAuthUser();

	// service for get one authUser and update authUser info by authUser id
	public AuthUser getAuthUser(int id);

	// delete an authUser
	public void deleteAuthUser(AuthUser authUser);
	
	public List<AuthUser> listAuthUsersByRoleId(int roleId);
	
	public AuthUser getAuthUserByUserId(String userId);
}
