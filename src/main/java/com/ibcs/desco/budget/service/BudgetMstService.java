package com.ibcs.desco.budget.service;

import java.util.List;

import com.ibcs.desco.budget.model.BudgetDtl;
import com.ibcs.desco.budget.model.BudgetMst;

public interface BudgetMstService {
	BudgetMst getOneByBudgetSession(Integer sessionId);
	
	BudgetMst getOneByBudgetSessionAndType(Integer sessionId, String budgetType);
	
	List<BudgetDtl> getValidBgtDtlListByMst(Integer budgetMstId);

	List<BudgetDtl> getReviceBgtDtlListByMst(Integer budgetMstId);
}
