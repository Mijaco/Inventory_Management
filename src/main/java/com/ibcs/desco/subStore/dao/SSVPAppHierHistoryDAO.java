package com.ibcs.desco.subStore.dao;

import java.util.List;

import com.ibcs.desco.subStore.model.SSVehiclePermissionApprovalHierarchyHistory;

public interface SSVPAppHierHistoryDAO {
	
	public List<SSVehiclePermissionApprovalHierarchyHistory> getSSVpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public SSVehiclePermissionApprovalHierarchyHistory getSSVpApprovalHierarchyHistory(int id);

}
