package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.ApprovalHierarchyDao;
import com.ibcs.desco.common.model.ApprovalHierarchy;

@Service
public class ApprovalHierarchyServiceImpl implements ApprovalHierarchyService {

	ApprovalHierarchyDao approvalHierarchyDao;

	public ApprovalHierarchyDao getApprovalHierarchyDao() {
		return approvalHierarchyDao;
	}

	public void setApprovalHierarchyDao(
			ApprovalHierarchyDao approvalHierarchyDao) {
		this.approvalHierarchyDao = approvalHierarchyDao;
	}

	@Override
	public void addApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {
		// TODO Auto-generated method stub
		approvalHierarchyDao.addApprovalHierarchy(approvalHierarchy);
	}

	@Override
	public List<ApprovalHierarchy> listApprovalHierarchys() {
		// TODO Auto-generated method stub
		return approvalHierarchyDao.listApprovalHierarchys();
	}

	@Override
	public ApprovalHierarchy getApprovalHierarchy(int id) {
		// TODO Auto-generated method stub
		return approvalHierarchyDao.getApprovalHierarchy(id);
	}

	@Override
	public void deleteApprovalHierarchy(ApprovalHierarchy approvalHierarchy) {
		// TODO Auto-generated method stub
		approvalHierarchyDao.deleteApprovalHierarchy(approvalHierarchy);
	}

	@Override
	public List<ApprovalHierarchy> getApprovalHierarchyByRoleName(
			String roleName) {
		// TODO Auto-generated method stub
		return approvalHierarchyDao.getApprovalHierarchyByRoleName(roleName);
	}

	@Override
	public List<ApprovalHierarchy> getApprovalHierarchyByOperationName(
			String operationName) {
		// TODO Auto-generated method stub
		return approvalHierarchyDao
				.getApprovalHierarchyByOperationName(operationName);
	}

	@Override
	public ApprovalHierarchy getApprovalHierarchyByStateCode(int stateCode) {
		// TODO Auto-generated method stub
		return approvalHierarchyDao.getApprovalHierarchyByStateCode(stateCode);
	}

	@Override
	public ApprovalHierarchy getApprovalHierarchyByRoleAndOperation(
			String roleName, String operationName) {
		// TODO Auto-generated method stub
		return approvalHierarchyDao.getApprovalHierarchyByRoleAndOperation(
				roleName, operationName);
	}

	@Override
	public ApprovalHierarchy getApprovalHierarchyByOperationNameAndSateCode(
			String operationName, String stateCode) {
		// TODO Auto-generated method stub
		return approvalHierarchyDao.getApprovalHierarchyByOperationNameAndSateCode(operationName, stateCode);
	}

	@Override
	public List<ApprovalHierarchy> getApprovalHierarchyByOperationNameAndRoleName(
			String operationName, List<String> roleNameList) {
		// TODO Auto-generated method stub
		return approvalHierarchyDao.getApprovalHierarchyByOperationNameAndRoleName(operationName, roleNameList);
	}
	
	//Added by Ihteshamul Alam, IBCS
	@Override
	public List<ApprovalHierarchy> getDistinctObjectByColumnName( ) {
		return approvalHierarchyDao.getDistinctObjectByColumnName( );
	}
	
	@Override
	public List<Object> getUserRoleByOperationName( String tableName, String columnName, String columnValue) {
		return approvalHierarchyDao.getUserRoleByOperationName( tableName, columnName, columnValue );
	}

	@Override
	public List<ApprovalHierarchy> getApprovalHierarchyByOperationNameAndRoleNameAndRangeIndicator(
			String operationName, List<String> roleNameList,
			String rangeIndicator) {
		// TODO Auto-generated method stub
		return approvalHierarchyDao.getApprovalHierarchyByOperationNameAndRoleNameAndRangeIndicator(
				operationName, roleNameList, rangeIndicator);
	}

}
