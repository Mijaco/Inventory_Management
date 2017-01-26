package com.ibcs.desco.cs.service;

import java.util.List;

import com.ibcs.desco.cs.model.VehiclePermissionApprovalHierarchyHistory;

public interface VPAppHierHistoryService {
	
	public List<VehiclePermissionApprovalHierarchyHistory> getvpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status);
	
	public VehiclePermissionApprovalHierarchyHistory getvpApprovalHierarchyHistory(int id);

}
