package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.VehiclePermissionApprovalHierarchyHistoryDAO;
import com.ibcs.desco.cs.model.VehiclePermissionApprovalHierarchyHistory;

@Service
public class VPAppHierHistoryServiceImpl implements VPAppHierHistoryService {
	
	private VehiclePermissionApprovalHierarchyHistoryDAO vpAppHierHistoryDAO;
	
	public VehiclePermissionApprovalHierarchyHistoryDAO getVpAppHierHistoryDAO() {
		return vpAppHierHistoryDAO;
	}

	public void setVpAppHierHistoryDAO(VehiclePermissionApprovalHierarchyHistoryDAO vpAppHierHistoryDAO) {
		this.vpAppHierHistoryDAO = vpAppHierHistoryDAO;
	}

	@Override
	public List<VehiclePermissionApprovalHierarchyHistory> getvpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return vpAppHierHistoryDAO.
				getvpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public VehiclePermissionApprovalHierarchyHistory getvpApprovalHierarchyHistory(int id) {
		// TODO Auto-generated method stub
		return vpAppHierHistoryDAO.getvpApprovalHierarchyHistory(id);
	}

}
