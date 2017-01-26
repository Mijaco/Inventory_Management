package com.ibcs.desco.cs.dao;

import java.util.List;

import com.ibcs.desco.cs.model.CentralStoreItems;

public interface CentralStoreItemsDao {
	// data access for add new CentralStoreItems
	public void addCentralStoreItems(CentralStoreItems centralStoreItems);

	// data access for get all CentralStoreItems as List
	public List<CentralStoreItems> listCentralStoreItems();

	// data access for get specific one CentralStoreItems information and
	// update
	// CentralStoreItems info
	public CentralStoreItems getCentralStoreItems(int id);
	
	public CentralStoreItems getCentralStoreItemsByItemId(String itemId);

	// data access for Delete an CentralStoreItems
	public void deleteCentralStoreItems(CentralStoreItems centralStoreItems);
}
