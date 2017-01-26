package com.ibcs.desco.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.admin.dao.UserDao;
import com.ibcs.desco.admin.model.AuthUser;
@Service
public class UserServiceImpl implements UserService {

	// userDao inject from servlet-context.xml
	private UserDao userDao;

	// set value from servlet-context.xml for userDao
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void addAuthUser(AuthUser authUser) {
		userDao.addAuthUser(authUser);
	}

	@Override
	public List<AuthUser> listAuthUser() {
		// TODO Auto-generated method stub
		return userDao.listAuthUsers();
	}

	@Override
	public AuthUser getAuthUser(int id) {
		// TODO Auto-generated method stub
		return userDao.getAuthUser(id);
	}

	@Override
	public void deleteAuthUser(AuthUser authUser) {
		userDao.deleteAuthUser(authUser);
	}

	@Override
	public List<AuthUser> listAuthUsersByRoleId(int roleId) {
		return userDao.listAuthUsersByRoleId(roleId);
	}

	@Override
	public AuthUser getAuthUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return userDao.getAuthUserByUserId(userId);
	}

}
