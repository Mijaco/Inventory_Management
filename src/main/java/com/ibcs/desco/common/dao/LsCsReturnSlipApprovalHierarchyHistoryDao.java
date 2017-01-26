package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;

public interface LsCsReturnSlipApprovalHierarchyHistoryDao {
	public List<LsCsReturnSlipApprovalHierarchyHistory> getLsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public LsCsReturnSlipApprovalHierarchyHistory getLsCsReturnSlipApprovalHierarchyHistory(int id);
}
