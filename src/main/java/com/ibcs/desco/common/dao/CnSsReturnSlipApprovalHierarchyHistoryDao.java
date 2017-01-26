package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;

public interface CnSsReturnSlipApprovalHierarchyHistoryDao {
	public List<CnSsReturnSlipApprovalHierarchyHistory> getCnSsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public CnSsReturnSlipApprovalHierarchyHistory getCnSsReturnSlipApprovalHierarchyHistory(int id);
}
