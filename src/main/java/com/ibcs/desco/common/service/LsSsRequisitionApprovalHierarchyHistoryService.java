package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.LsSsRequisitionApprovalHierarchyHistory;

public interface LsSsRequisitionApprovalHierarchyHistoryService {
	public List<LsSsRequisitionApprovalHierarchyHistory> getLsSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public LsSsRequisitionApprovalHierarchyHistory getLsSsRequisitionApprovalHierarchyHistory(
			int id);
	public List<String> getOperationIdListByDeptIdAndStatus(String deptId,
			String status);
	
	public List<String> getOperationIdListByDeptIdAndStatusLSPDSS(String deptId,
			String status);
}
