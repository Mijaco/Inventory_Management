package com.ibcs.desco.test.dao;

import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;

public interface ExceToDbDao {
	
	void saveItemCategory(ItemCategory itemCategory);
	
	void saveItemMaster(ItemMaster itemMaster);
}
