package com.ibcs.desco.subStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.subStore.dao.SSVPAppHierHistoryDAO;
import com.ibcs.desco.subStore.model.SSVehiclePermissionApprovalHierarchyHistory;

@Service
public class SSVPAppHierHistoryServiceImpl implements SSVPAppHierHistoryService {
	
	private SSVPAppHierHistoryDAO ssVpAppHierHistoryDAO;
	
	

	public SSVPAppHierHistoryDAO getSsVpAppHierHistoryDAO() {
		return ssVpAppHierHistoryDAO;
	}

	public void setSsVpAppHierHistoryDAO(SSVPAppHierHistoryDAO ssVpAppHierHistoryDAO) {
		this.ssVpAppHierHistoryDAO = ssVpAppHierHistoryDAO;
	}

	@Override
	public List<SSVehiclePermissionApprovalHierarchyHistory> getSSVpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return ssVpAppHierHistoryDAO.
				getSSVpApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public SSVehiclePermissionApprovalHierarchyHistory getSSVpApprovalHierarchyHistory(int id) {
		// TODO Auto-generated method stub
		return ssVpAppHierHistoryDAO.getSSVpApprovalHierarchyHistory(id);
	}

}
