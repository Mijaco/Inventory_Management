package com.ibcs.desco.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.budget.dao.BudgetMstDao;
import com.ibcs.desco.budget.model.BudgetDtl;
import com.ibcs.desco.budget.model.BudgetMst;

@Service
public class BudgetMstServiceImpl implements BudgetMstService {

	private BudgetMstDao budgetMstDao;


	public BudgetMstDao getBudgetMstDao() {
		return budgetMstDao;
	}

	public void setBudgetMstDao(BudgetMstDao budgetMstDao) {
		this.budgetMstDao = budgetMstDao;
	}

	@Override
	public BudgetMst getOneByBudgetSession(Integer sessionId) {
		
		return budgetMstDao.getOneByBudgetSession(sessionId);
	}

	@Override
	public BudgetMst getOneByBudgetSessionAndType(Integer sessionId,
			String budgetType) {
		return budgetMstDao.getOneByBudgetSessionAndType(sessionId, budgetType);
	}

	@Override
	public List<BudgetDtl> getValidBgtDtlListByMst(Integer budgetMstId) {
		System.out.println(budgetMstId);
		return budgetMstDao.getValidBgtDtlListByMst(budgetMstId);
	}
	@Override
	public List<BudgetDtl> getReviceBgtDtlListByMst(Integer budgetMstId) {
		System.out.println(budgetMstId);
		return budgetMstDao.getReviceBgtDtlListByMst(budgetMstId);
	}

	
	
}
