package com.ibcs.desco.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.inventory.dao.OpeningBalanceDao;
import com.ibcs.desco.inventory.model.OpeningBalance;

@Service
public class OpeningBalanceServiceIimpl implements OpeningBalanceService {

	@Autowired
	OpeningBalanceDao openingBalanceDao;

	@Override
	public void createOpeingBalance(OpeningBalance openingBalance) {
		openingBalanceDao.createOpeingBalance(openingBalance);

	}

	@Override
	public List<OpeningBalance> getAllIOpeningBalance() {
		return openingBalanceDao.getAllIOpeningBalance();
	}

	@Override
	public OpeningBalance getOpenBalanceById(int id) {

		return openingBalanceDao.getOpenBalanceById(id);
	}

	@Override
	public void editOpenBalance(OpeningBalance openBalance) {
		openingBalanceDao.editOpeningBalance(openBalance);

	}

	public List<OpeningBalance> getOpeningBalanceByItemName(String itemName){
		return openingBalanceDao.getOpeningBalanceByItemName(itemName);
	}
}
