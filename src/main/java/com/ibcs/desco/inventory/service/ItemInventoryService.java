package com.ibcs.desco.inventory.service;

import java.util.Date;
import java.util.List;

import com.ibcs.desco.inventory.model.AvgPrice;
import com.ibcs.desco.inventory.model.ItemMaster;

public interface ItemInventoryService {

	void addItemInventory(ItemMaster itemMaster);

	List<ItemMaster> getInventoryItemList();

	ItemMaster getItemGroup(int id);

	void deleteItemGroup();
	
	List<ItemMaster> getItemListByCategoryId(int categoryId);

	List<ItemMaster> getInventoryItemForAutoComplete(String itemName);

	ItemMaster getInventoryItemById(int id);

	void editInventoryItem(ItemMaster itemMaster);

	List<ItemMaster> getInventoryItemByName(String itemName);

	ItemMaster getInventoryItemByItemName(String itemName);

	AvgPrice getAvgPriceByItemCode(String itemCode);

	String getItemNameByItemCode(String itemCode);

	List<Date> getDistinctInventoryDateListFromPhysicalInventory();
}
