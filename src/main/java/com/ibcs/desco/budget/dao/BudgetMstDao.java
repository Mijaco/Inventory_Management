package com.ibcs.desco.budget.dao;

import java.util.List;

import com.ibcs.desco.budget.model.BudgetDtl;
import com.ibcs.desco.budget.model.BudgetMst;

public interface BudgetMstDao {

	BudgetMst getOneByBudgetSession(Integer id);
	
	BudgetMst getOneByBudgetSessionAndType(Integer sessionId, String budgetType);

	List<BudgetDtl> getValidBgtDtlListByMst(Integer budgetMstId);

	List<BudgetDtl> getReviceBgtDtlListByMst(Integer budgetMstId);
}
