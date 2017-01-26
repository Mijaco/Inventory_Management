package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.LsCsReturnSlipApprovalHierarchyHistory;

public interface LsCsReturnSlipApprovalHierarchyHistoryService {
	public List<LsCsReturnSlipApprovalHierarchyHistory> getLsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public LsCsReturnSlipApprovalHierarchyHistory getLsCsReturnSlipApprovalHierarchyHistory(
			int id);
}
