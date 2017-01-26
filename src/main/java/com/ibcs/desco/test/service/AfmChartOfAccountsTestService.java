package com.ibcs.desco.test.service;

import java.util.List;

import com.ibcs.desco.test.model.AfmChartOfAccountsTest;

public interface AfmChartOfAccountsTestService {
	// data access for add new AfmChartOfAccountsTest
			public void addCentralStoreItems(AfmChartOfAccountsTest afmChartOfAccountsTest);

			// data access for get all AfmChartOfAccountsTest as List
			public List<AfmChartOfAccountsTest> listAfmChartOfAccountsTest();

			// data access for get specific one AfmChartOfAccountsTest information and
			// update
			// AfmChartOfAccountsTest info
			public AfmChartOfAccountsTest getAfmChartOfAccountsTest(long id);

			public AfmChartOfAccountsTest getAfmChartOfAccountsTestByCode(Long code);
			// data access for Delete an AfmChartOfAccountsTest
			public void deleteAfmChartOfAccountsTest(AfmChartOfAccountsTest afmChartOfAccountsTest);
}
