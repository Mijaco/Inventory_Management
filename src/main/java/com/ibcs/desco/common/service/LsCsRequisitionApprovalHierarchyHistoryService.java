package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;

public interface LsCsRequisitionApprovalHierarchyHistoryService {
	public List<LsCsRequisitionApprovalHierarchyHistory> getLsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public LsCsRequisitionApprovalHierarchyHistory getLsCsRequisitionApprovalHierarchyHistory(
			int id);
	
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status);
}
