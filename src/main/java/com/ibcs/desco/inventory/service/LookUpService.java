package com.ibcs.desco.inventory.service;

import java.util.List;

import com.ibcs.desco.inventory.model.AllLookUp;

public interface LookUpService {

	void addLokUp(AllLookUp lookUp);

	List<AllLookUp> getAllLookUps();
	
	List<AllLookUp> getAllLookUpParents();

	AllLookUp getLookUps(int id);

	void deleteLookUps();

	List<Integer> getParentIdList();

	Integer getParentID(String title);

	AllLookUp getLookUpById(int id);

	void editLookUp(AllLookUp lookUp);

	List<AllLookUp> getLookUpByKeyword(String keyword);
	
	Integer getParentIdByParentName(String parentName);
	
	List<AllLookUp> getAllLookupByParentname(String parentName);

}
