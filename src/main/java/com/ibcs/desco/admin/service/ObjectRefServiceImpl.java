package com.ibcs.desco.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.admin.dao.ObjectRefDao;
import com.ibcs.desco.admin.model.ObjectReference;

@Service
public class ObjectRefServiceImpl implements ObjectRefService {
	
	private ObjectRefDao objectRefDao;

	public ObjectRefDao getObjectRefDao() {
		return objectRefDao;
	}

	public void setObjectRefDao(ObjectRefDao objectRefDao) {
		this.objectRefDao = objectRefDao;
	}

	@Override
	public List<ObjectReference> listObjects() {
		// TODO Auto-generated method stub
		return objectRefDao.listObjects();
	}
	
	@Override
	public ObjectReference getObjectReference(int id) {
		// TODO Auto-generated method stub
		return objectRefDao.getObjectReference(id);
	}

}
