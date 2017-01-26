package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.LsSsReturnSlipApprovalHierarchyHistory;

public interface LsSsReturnSlipApprovalHierarchyHistoryService {
	public List<LsSsReturnSlipApprovalHierarchyHistory> getLsSsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public LsSsReturnSlipApprovalHierarchyHistory getLsSsReturnSlipApprovalHierarchyHistory(
			int id);
}
