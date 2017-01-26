package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.LsCsRequisitionApprovalHierarchyHistory;

public interface LsCsRequisitionApprovalHierarchyHistoryDao {
	public List<LsCsRequisitionApprovalHierarchyHistory> getLsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public LsCsRequisitionApprovalHierarchyHistory getLsCsRequisitionApprovalHierarchyHistory(
			int id);

	public List<String> getOperationIdListByDeptIdAndStatus(
			String deptId, String status);
}
