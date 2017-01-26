package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.CnSsReturnSlipApprovalHierarchyHistory;

public interface CnSsReturnSlipApprovalHierarchyHistoryService {
	public List<CnSsReturnSlipApprovalHierarchyHistory> getCnSsReturnSlipApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public CnSsReturnSlipApprovalHierarchyHistory getCnSsReturnSlipApprovalHierarchyHistory(
			int id);
}
