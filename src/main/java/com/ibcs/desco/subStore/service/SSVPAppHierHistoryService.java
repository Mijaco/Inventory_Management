package com.ibcs.desco.subStore.service;

import java.util.List;

import com.ibcs.desco.subStore.model.SSVehiclePermissionApprovalHierarchyHistory;

public interface SSVPAppHierHistoryService {
	
	public List<SSVehiclePermissionApprovalHierarchyHistory> getSSVpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public SSVehiclePermissionApprovalHierarchyHistory getSSVpApprovalHierarchyHistory(int id);

}
