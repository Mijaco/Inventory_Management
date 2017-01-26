package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;

public interface SsCsReturnSlipApprovalHierarchyHistoryDao {
	public List<SsCsReturnSlipApprovalHierarchyHistory> getSsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public SsCsReturnSlipApprovalHierarchyHistory getSsCsReturnSlipApprovalHierarchyHistory(int id);
}
