package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.AsBuiltApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.AsBuiltApprovalHierarchyHistory;

@Service
public class AsBuiltApprovalHierarchyHistoryServiceImpl implements
AsBuiltApprovalHierarchyHistoryService {

	private AsBuiltApprovalHierarchyHistoryDao asBuiltApprovalHierarchyHistoryDao;

	
	
	public AsBuiltApprovalHierarchyHistoryDao getAsBuiltApprovalHierarchyHistoryDao() {
		return asBuiltApprovalHierarchyHistoryDao;
	}

	public void setAsBuiltApprovalHierarchyHistoryDao(
			AsBuiltApprovalHierarchyHistoryDao asBuiltApprovalHierarchyHistoryDao) {
		this.asBuiltApprovalHierarchyHistoryDao = asBuiltApprovalHierarchyHistoryDao;
	}

	@Override
	public List<AsBuiltApprovalHierarchyHistory> getAsBuiltApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return asBuiltApprovalHierarchyHistoryDao
				.getAsBuiltApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public AsBuiltApprovalHierarchyHistory getAsBuiltApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return asBuiltApprovalHierarchyHistoryDao.getAsBuiltApprovalHierarchyHistory(id);
	}

}
