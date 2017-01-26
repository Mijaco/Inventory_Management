package com.ibcs.desco.contractor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.contractor.dao.ContractorRepresentiveDao;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.contractor.model.ContractorRepresentive;

@Service
public class ContractorRepresentiveServiceImpl implements ContractorRepresentiveService {

	@Autowired
	ContractorRepresentiveDao contractorRepresentiveDao;

	public ContractorRepresentiveDao getContractorRepresentiveDao() {
		return contractorRepresentiveDao;
	}

	public void setContractorRepresentiveDao(ContractorRepresentiveDao contractorRepresentiveDao) {
		this.contractorRepresentiveDao = contractorRepresentiveDao;
	}
	public boolean isValidContractor(String userName) {
		return contractorRepresentiveDao.isValidContractor(userName);
	}
	public ContractorRepresentive getContractorRep(String userId){
		return contractorRepresentiveDao.getContractorRep(userId);

	}
	public Contractor getContractorByContractNo(String contractNo){
		return contractorRepresentiveDao.getContractorByContractNo(contractNo);

	}
	public boolean getValidContractor(String contractNo){
		return contractorRepresentiveDao.getValidContractor(contractNo);

	}
	
	public List<Contractor> getContractorList(){
		
		return contractorRepresentiveDao.getContractorList();
	}
	
	public void addContractor(Contractor contractor){
		contractorRepresentiveDao.addContractor(contractor);
	}
	
	public void addContractorRepresentive(ContractorRepresentive contractorRepresentive){
		contractorRepresentiveDao.addContractorRepresentive(contractorRepresentive);
		
	}
	/*

	@Override
	public void addItemInventory(ItemMaster itemMaster) {
		itemInventoryDao.addItemInventory(itemMaster);

	}

	@Override
	public List<ItemMaster> getInventoryItemList() {
		return itemInventoryDao.getInventoryItemList();

	}

	@Override
	public ItemMaster getItemGroup(int id) {
		return itemInventoryDao.getItemGroup(id);
	}

	@Override
	public void deleteItemGroup() {

	}

	@Override
	public List<ItemMaster> getInventoryItemForAutoComplete(String itemName) {

		List<ItemMaster> inventoryItemListForAutoComplete = new ArrayList<ItemMaster>();
		List<ItemMaster> inventoryItemList = itemInventoryDao.getInventoryItemList();

		for (ItemMaster itemMaster : inventoryItemList) {
			inventoryItemListForAutoComplete.add(itemMaster);
		}

		return inventoryItemListForAutoComplete;
	}

	@Override
	public ItemMaster getInventoryItemById(int id) {
		return itemInventoryDao.getInventoryItemById(id);
	}

	@Override
	public void editInventoryItem(ItemMaster itemMaster) {
		itemInventoryDao.editInventoryItem(itemMaster);

	}

	@Override
	public List<ItemMaster> getInventoryItemByName(String itemName) {
		return itemInventoryDao.getInventoryItemByName(itemName);
	}

	@Override
	public List<ItemMaster> getItemListByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return itemInventoryDao.getItemListByCategoryId(categoryId);
	}
*/
}
