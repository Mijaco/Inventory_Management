package com.ibcs.desco.contractor.service;


import java.util.List;

import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;


public interface ContractorRepresentiveService {
	
	boolean isValidContractor(String userName);
	
	ContractorRepresentive getContractorRep(String userId);
	
	boolean getValidContractor(String contractNo);
	
	Contractor getContractorByContractNo(String contractNo);
	
	void addContractor(Contractor contractor);
	
	List<Contractor> getContractorList();
	
	void addContractorRepresentive(ContractorRepresentive contractorRepresentive);
	
	/*

	void addItemInventory(ItemMaster itemMaster);

	List<ItemMaster> getInventoryItemList();

	ItemMaster getItemGroup(int id);

	void deleteItemGroup();
	isValidy
	
	List<ItemMaster> getItemListByCategoryId(int categoryId);

	List<ItemMaster> getInventoryItemForAutoComplete(String itemName);

	ItemMaster getInventoryItemById(int id);

	void editInventoryItem(ItemMaster itemMaster);

	List<ItemMaster> getInventoryItemByName(String itemName);*/
}
