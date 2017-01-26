package com.ibcs.desco.subStore.dao;

import java.util.List;

import com.ibcs.desco.subStore.model.SubStoreItems;

public interface SubStoreItemsDao {
	// data access for add new SubStoreItems
	public void addSubStoreItems(SubStoreItems subStoreItems);

	// data access for get all SubStoreItems as List
	public List<SubStoreItems> listSubStoreItems();

	// data access for get specific one SubStoreItems information and
	// update
	// SubStoreItems info
	public SubStoreItems getSubStoreItems(int id);
	
	public SubStoreItems getSubStoreItemsByItemId(String itemId);

	// data access for Delete an SubStoreItems
	public void deleteSubStoreItems(SubStoreItems subStoreItems);
}
