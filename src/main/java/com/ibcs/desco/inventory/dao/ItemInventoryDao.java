package com.ibcs.desco.inventory.dao;

import java.util.Date;
import java.util.List;

import com.ibcs.desco.inventory.model.AvgPrice;
import com.ibcs.desco.inventory.model.ItemMaster;

public interface ItemInventoryDao {

	void addItemInventory(ItemMaster itemMaster);

	List<ItemMaster> getInventoryItemList();
	
	List<ItemMaster> getItemListByCategoryId(int categoryId);

	ItemMaster getItemGroup(int id);

	void deleteItemGroup();

	List<ItemMaster> getInventoryItemForAutoComplete(String itemName);

	ItemMaster getInventoryItemById(int id);

	void editInventoryItem(ItemMaster itemMaster);

	List<ItemMaster> getInventoryItemByName(String itemName);

	ItemMaster getInventoryItemByItemName(String itemName);

	AvgPrice getAvgPriceByItemCode(String itemCode);

	List<Date> getDistinctInventoryDateListFromPhysicalInventory();

	String getItemNameByItemCode(String itemCode);
}
