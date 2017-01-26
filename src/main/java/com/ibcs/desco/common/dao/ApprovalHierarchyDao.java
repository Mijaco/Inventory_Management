package com.ibcs.desco.common.dao;

import java.util.List;

import com.ibcs.desco.common.model.ApprovalHierarchy;

public interface ApprovalHierarchyDao {
	// data access for add new ApprovalHierarchy
	public void addApprovalHierarchy(ApprovalHierarchy approvalHierarchy);

	// data access for get all ApprovalHierarchy as List
	public List<ApprovalHierarchy> listApprovalHierarchys();

	// data access for get specific one ApprovalHierarchy information and update
	// AppovalHierarchy info
	public ApprovalHierarchy getApprovalHierarchy(int id);

	// data access for Delete an ApprovalHierarchy
	public void deleteApprovalHierarchy(ApprovalHierarchy approvalHierarchy);

	public List<ApprovalHierarchy> getApprovalHierarchyByRoleName(String roleName);

	public List<ApprovalHierarchy> getApprovalHierarchyByOperationName(String operationName);
	
	public ApprovalHierarchy getApprovalHierarchyByOperationNameAndSateCode(String operationName, String stateCode);
	
	public ApprovalHierarchy getApprovalHierarchyByStateCode(int stateCode);
	
	public ApprovalHierarchy getApprovalHierarchyByRoleAndOperation(String roleName, String operationName);

	List<ApprovalHierarchy> getApprovalHierarchyByOperationNameAndRoleName(
			String operationName, List<String> roleNameList);
	
	//Added by: Ihteshamul Alam, IBCS
	public List<ApprovalHierarchy>getDistinctObjectByColumnName( );
	
	public List<Object> getUserRoleByOperationName( String tableName, String columnName, String columnValue);

	public List<ApprovalHierarchy> getApprovalHierarchyByOperationNameAndRoleNameAndRangeIndicator(
			String operationName, List<String> roleNameList,
			String rangeIndicator);
}
