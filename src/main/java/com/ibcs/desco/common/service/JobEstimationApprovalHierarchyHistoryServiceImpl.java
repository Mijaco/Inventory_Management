package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.JobEstimationApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.JobEstimationApprovalHierarchyHistory;

@Service
public class JobEstimationApprovalHierarchyHistoryServiceImpl implements
JobEstimationApprovalHierarchyHistoryService {

	private JobEstimationApprovalHierarchyHistoryDao jobEstimationApprovalHierarchyHistoryDao;

	public JobEstimationApprovalHierarchyHistoryDao getJobEstimationApprovalHierarchyHistoryDao() {
		return jobEstimationApprovalHierarchyHistoryDao;
	}

	public void setJobEstimationApprovalHierarchyHistoryDao(
			JobEstimationApprovalHierarchyHistoryDao JobEstimationApprovalHierarchyHistoryDao) {
		this.jobEstimationApprovalHierarchyHistoryDao = JobEstimationApprovalHierarchyHistoryDao;
	}

	@Override
	public List<JobEstimationApprovalHierarchyHistory> getJobEstimationApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
			String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		return jobEstimationApprovalHierarchyHistoryDao
				.getJobEstimationApprovalHierarchyHistoryByDeptIdAndStatusAndRole(
						deptId, roleName, status);
	}

	@Override
	public JobEstimationApprovalHierarchyHistory getJobEstimationApprovalHierarchyHistory(
			int id) {
		// TODO Auto-generated method stub
		return jobEstimationApprovalHierarchyHistoryDao.getJobEstimationApprovalHierarchyHistory(id);
	}

}
