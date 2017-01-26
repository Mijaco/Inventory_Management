package com.ibcs.desco.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.inventory.dao.LookUpDao;
import com.ibcs.desco.inventory.model.AllLookUp;

@Service
public class LookUpServiceImpl implements LookUpService {

	@Autowired
	LookUpDao lookUpDao;

	@Override
	public void addLokUp(AllLookUp lookUp) {
		lookUpDao.addLokUp(lookUp);

	}

	@Override
	public List<AllLookUp> getAllLookUps() {
		return lookUpDao.getAllLookUps();
	}

	@Override
	public AllLookUp getLookUps(int id) {
		return null;
	}

	@Override
	public void deleteLookUps() {

	}

	public List<Integer> getParentIdList() {
		return lookUpDao.getParentIdList();
	}

	@Override
	public Integer getParentID(String title) {
		return lookUpDao.getParentId(title);
	}

	@Override
	public AllLookUp getLookUpById(int id) {
		return lookUpDao.getLookUpById(id);
	}

	@Override
	public void editLookUp(AllLookUp lookUp) {
		lookUpDao.editLookUp(lookUp);

	}

	@Override
	public List<AllLookUp> getLookUpByKeyword(String keyword) {
		return lookUpDao.getLookUpByKeyword(keyword);
	}

	@Override
	public Integer getParentIdByParentName(String parentName) {
		return lookUpDao.getParentIdByParentName(parentName);
	}

	@Override
	public List<AllLookUp> getAllLookupByParentname(String parentName) {
		return lookUpDao.getAllLookupByParentname(parentName);
	}

	@Override
	public List<AllLookUp> getAllLookUpParents() {
		// TODO Auto-generated method stub
		return lookUpDao.getAllLookUpParents();
	};

}
