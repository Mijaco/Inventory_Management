package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.SsCsReturnSlipApprovalHierarchyHistory;

public interface SsCsReturnSlipApprovalHierarchyHistoryService {
	public List<SsCsReturnSlipApprovalHierarchyHistory> getSsCsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public SsCsReturnSlipApprovalHierarchyHistory getSsCsReturnSlipApprovalHierarchyHistory(
			int id);
}
