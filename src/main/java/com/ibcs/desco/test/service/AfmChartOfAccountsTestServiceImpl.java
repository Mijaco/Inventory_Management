package com.ibcs.desco.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibcs.desco.test.dao.AfmChartOfAccountsTestDao;
import com.ibcs.desco.test.model.AfmChartOfAccountsTest;

@Service
public class AfmChartOfAccountsTestServiceImpl implements
		AfmChartOfAccountsTestService {

	
	//@Autowired
	private AfmChartOfAccountsTestDao afmChartOfAccountsTestDao;
	
	
	public AfmChartOfAccountsTestDao getAfmChartOfAccountsTestDao() {
		return afmChartOfAccountsTestDao;
	}

	public void setAfmChartOfAccountsTestDao(
			AfmChartOfAccountsTestDao afmChartOfAccountsTestDao) {
		this.afmChartOfAccountsTestDao = afmChartOfAccountsTestDao;
	}

	@Override
	public void addCentralStoreItems(
			AfmChartOfAccountsTest afmChartOfAccountsTest) {
		afmChartOfAccountsTestDao.addCentralStoreItems(afmChartOfAccountsTest);

	}

	@Override
	public List<AfmChartOfAccountsTest> listAfmChartOfAccountsTest() {
		// TODO Auto-generated method stub
		return afmChartOfAccountsTestDao.listAfmChartOfAccountsTest();
	}

	@Override
	public AfmChartOfAccountsTest getAfmChartOfAccountsTest(long id) {
		// TODO Auto-generated method stub
		return afmChartOfAccountsTestDao.getAfmChartOfAccountsTest(id);
	}

	@Override
	public AfmChartOfAccountsTest getAfmChartOfAccountsTestByCode(Long code) {
		// TODO Auto-generated method stub
		return afmChartOfAccountsTestDao.getAfmChartOfAccountsTestByCode(code);
	}

	@Override
	public void deleteAfmChartOfAccountsTest(
			AfmChartOfAccountsTest afmChartOfAccountsTest) {
		afmChartOfAccountsTestDao.deleteAfmChartOfAccountsTest(afmChartOfAccountsTest);

	}

}
