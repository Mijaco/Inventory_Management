package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.CnSsRequisitionApprovalHierarchyHistory;

public interface CnSsRequisitionApprovalHierarchyHistoryDao {
	public List<CnSsRequisitionApprovalHierarchyHistory> getCnSsRequisitionApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public CnSsRequisitionApprovalHierarchyHistory getCnSsRequisitionApprovalHierarchyHistory(int id);
}
