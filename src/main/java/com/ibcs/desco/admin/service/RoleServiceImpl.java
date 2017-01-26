package com.ibcs.desco.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.admin.dao.RoleDao;
import com.ibcs.desco.admin.model.Roles;

@Service
public class RoleServiceImpl implements RoleService {

	// roleDao inject from servlet-context.xml
	private RoleDao roleDao;

	// set value from servlet-context.xml for roleDao
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public void addRoles(Roles role) {
		roleDao.addRoles(role);
	}

	@Override
	public List<Roles> listRoles() {
		// TODO Auto-generated method stub
		return roleDao.listRoles();
	}

	@Override
	public Roles getRoles(int id) {
		// TODO Auto-generated method stub
		return roleDao.getRoles(id);
	}

	@Override
	public void deleteRoles(Roles role) {
		roleDao.deleteRoles(role);
	}

}
