package com.ibcs.desco.inventory.dao;

import java.util.List;

import com.ibcs.desco.inventory.model.OpeningBalance;

public interface OpeningBalanceDao {

	void createOpeingBalance(OpeningBalance openingBalance);

	List<OpeningBalance> getAllIOpeningBalance();

	OpeningBalance getOpenBalanceById(int id);

	void editOpeningBalance(OpeningBalance openingBalance);

	List<OpeningBalance> getOpeningBalanceByItemName(String itemName);

}
