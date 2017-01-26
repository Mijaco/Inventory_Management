package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.SsCsRequisitionApprovalHierarchyHistory;

public interface SsCsRequisitionApprovalHierarchyHistoryDao {
	public List<SsCsRequisitionApprovalHierarchyHistory> getSsCsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public SsCsRequisitionApprovalHierarchyHistory getSsCsRequisitionApprovalHierarchyHistory(int id);
	
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status);
}
