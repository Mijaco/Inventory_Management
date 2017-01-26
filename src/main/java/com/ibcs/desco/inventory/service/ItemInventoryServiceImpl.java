package com.ibcs.desco.inventory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.inventory.dao.ItemInventoryDao;
import com.ibcs.desco.inventory.model.AvgPrice;
import com.ibcs.desco.inventory.model.ItemMaster;

@Service
public class ItemInventoryServiceImpl implements ItemInventoryService {

	@Autowired
	ItemInventoryDao itemInventoryDao;

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
	public ItemMaster getInventoryItemByItemName(String itemName) {
		return itemInventoryDao.getInventoryItemByItemName(itemName);
	}

	@Override
	public AvgPrice getAvgPriceByItemCode(String itemCode) {
		return itemInventoryDao.getAvgPriceByItemCode(itemCode);
	}

	@Override
	public List<ItemMaster> getItemListByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return itemInventoryDao.getItemListByCategoryId(categoryId);
	}
	
	@Override
	public List<Date> getDistinctInventoryDateListFromPhysicalInventory() {
		// TODO Auto-generated method stub
		return itemInventoryDao.getDistinctInventoryDateListFromPhysicalInventory();
	}


	@Override
	public String getItemNameByItemCode(String itemCode){
		
		return itemInventoryDao.getItemNameByItemCode(itemCode);
	}

}
