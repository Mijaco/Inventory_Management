package com.ibcs.desco.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.admin.dao.PermissionDao;
import com.ibcs.desco.admin.model.PermissionTable;
@Service
public class PermissionServiceImpl implements PermissionService {

	// permissionDao inject from servlet-context.xml
	private PermissionDao permissionDao;

	// set value from servlet-context.xml for permissionDao
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	@Override
	public void addPermissionTable(PermissionTable permission) {
		permissionDao.addPermission(permission);
	}

	@Override
	public List<PermissionTable> listPermissionTable() {
		// TODO Auto-generated method stub
		return permissionDao.listPermissions();
	}

	@Override
	public PermissionTable getPermissionTable(int id) {
		// TODO Auto-generated method stub
		return permissionDao.getPermissions(id);
	}

	@Override
	public void deletePermissionTable(PermissionTable permission) {
		permissionDao.deletePermission(permission);
	}

	@Override
	public List<PermissionTable> listPermissionByRoleId(int roleId) {
		// TODO Auto-generated method stub
		return permissionDao.listPermissionByRoleId(roleId);
	}

}
