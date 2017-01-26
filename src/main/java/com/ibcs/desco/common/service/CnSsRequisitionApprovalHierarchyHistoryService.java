package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;

public interface CnSsRequisitionApprovalHierarchyHistoryService {
	public List<CnSsRequisitionApprovalHierarchyHistory> getCnSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public CnSsRequisitionApprovalHierarchyHistory getCnSsRequisitionApprovalHierarchyHistory(
			int id);
}
