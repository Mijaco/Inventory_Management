package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.LsSsReturnSlipApprovalHierarchyHistory;

public interface LsSsReturnSlipApprovalHierarchyHistoryDao {
	public List<LsSsReturnSlipApprovalHierarchyHistory> getLsSsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public LsSsReturnSlipApprovalHierarchyHistory getLsSsReturnSlipApprovalHierarchyHistory(int id);
}
