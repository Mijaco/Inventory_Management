package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.AsBuiltApprovalHierarchyHistory;

public interface AsBuiltApprovalHierarchyHistoryService {
	public List<AsBuiltApprovalHierarchyHistory> getAsBuiltApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);

	public AsBuiltApprovalHierarchyHistory getAsBuiltApprovalHierarchyHistory(
			int id);
}
