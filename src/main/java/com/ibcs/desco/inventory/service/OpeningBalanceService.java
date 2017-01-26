package com.ibcs.desco.inventory.service;

import java.util.List;

import com.ibcs.desco.inventory.model.OpeningBalance;

public interface OpeningBalanceService {

	void createOpeingBalance(OpeningBalance openingBalance);

	List<OpeningBalance> getAllIOpeningBalance();

	OpeningBalance getOpenBalanceById(int id);

	void editOpenBalance(OpeningBalance openBalance);

	List<OpeningBalance> getOpeningBalanceByItemName(String itemName);

}
