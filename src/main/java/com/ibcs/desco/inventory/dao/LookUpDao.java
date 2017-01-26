package com.ibcs.desco.inventory.dao;

import java.util.List;

import com.ibcs.desco.inventory.model.AllLookUp;

public interface LookUpDao {

	void addLokUp(AllLookUp lookUp);
	
	List<AllLookUp> getAllLookUps();
	
	List<AllLookUp> getAllLookUpParents();
	
	AllLookUp getLookUps(int id);
	
	void deleteLookUps();
	
	List<Integer> getParentIdList();
	
	Integer getParentId(String title);
	
	Integer getParentIdByParentName(String parentName);
	
	AllLookUp getLookUpById(int id);

	void editLookUp(AllLookUp lookUp);

	List<AllLookUp> getLookUpByKeyword(String Keyword);
	
	List<AllLookUp> getAllLookupByParentname(String parentName);

	Integer getParentIdByTitle(String title);
	
}
