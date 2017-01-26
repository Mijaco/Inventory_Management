package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.GatePassApprovalHierarchyHistory;

public interface GatePassApprovalHierarchyHistoryService {
	public List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public  List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status);

	public GatePassApprovalHierarchyHistory getGatePassApprovalHierarchyHistory(
			int id);
	
	public List<GatePassApprovalHierarchyHistory> getGatePassApprovalHierarchyHistoryByDeptIdAndStatusAndRoleAndGPNo(
			String deptId, String roleName, String status, String gpNo);
}
