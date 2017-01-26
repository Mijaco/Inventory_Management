package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.dao.ItemRcvApprovalHierarchyHistoryDao;
import com.ibcs.desco.common.model.ItemRcvApprovalHierarchyHistory;

@Service
public class ItemRcvApprovalHierarchyHistoryServiceImpl implements
		ItemRcvApprovalHierarchyHistoryService {

	ItemRcvApprovalHierarchyHistoryDao itemRcvApprovalHierarchyHistoryDao;

	public ItemRcvApprovalHierarchyHistoryDao getItemRcvApprovalHierarchyHistoryDao() {
		return itemRcvApprovalHierarchyHistoryDao;
	}

	public void setItemRcvApprovalHierarchyHistoryDao(
			ItemRcvApprovalHierarchyHistoryDao itemRcvApprovalHierarchyHistoryDao) {
		this.itemRcvApprovalHierarchyHistoryDao = itemRcvApprovalHierarchyHistoryDao;
	}

	@Override
	public List<ItemRcvApprovalHierarchyHistory> listApprovalHierarchyHistorys() {
		// TODO Auto-generated method stub
		return itemRcvApprovalHierarchyHistoryDao
				.listApprovalHierarchyHistorys();
	}

	@Override
	public ItemRcvApprovalHierarchyHistory getApprovalHierarchyHistory(int id) {
		// TODO Auto-generated method stub
		return itemRcvApprovalHierarchyHistoryDao
				.getApprovalHierarchyHistory(id);
	}

	@Override
	public void deleteApprovalHierarchyHistory(
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory) {
		// TODO Auto-generated method stub
		itemRcvApprovalHierarchyHistoryDao
				.deleteApprovalHierarchyHistory(approvalHierarchyHistory);
	}

	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByRoleName(
			String actRoleName) {
		// TODO Auto-generated method stub
		return itemRcvApprovalHierarchyHistoryDao
				.getApprovalHierarchyHistoryByRoleName(actRoleName);
	}

	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByStateCode(
			int stateCode) {
		// TODO Auto-generated method stub
		return itemRcvApprovalHierarchyHistoryDao
				.getApprovalHierarchyHistoryByStateCode(stateCode);
	}

	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOperationId(
			String operationId) {
		// TODO Auto-generated method stub
		return itemRcvApprovalHierarchyHistoryDao
				.getApprovalHierarchyHistoryByOperationId(operationId);
	}

	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByStatusAndStateCode(
			int stateCode, String status) {
		// TODO Auto-generated method stub
		return itemRcvApprovalHierarchyHistoryDao
				.getApprovalHierarchyHistoryByStatusAndStateCode(stateCode,
						status);
	}

	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status) {
		// TODO Auto-generated method stub
		return itemRcvApprovalHierarchyHistoryDao
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
						operationName, operationId, status);
	}

	@Override
	public List<ItemRcvApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
			String operationName, String roleName, String status) {
		// TODO Auto-generated method stub
		return itemRcvApprovalHierarchyHistoryDao
				.getApprovalHierarchyHistoryByOppNameOppIdAndStatusAndRole(
						operationName, roleName, status);
	}

	@Override
	public void addApprovalHierarchyHistory(
			ItemRcvApprovalHierarchyHistory approvalHierarchyHistory) {
		// TODO Auto-generated method stub
		itemRcvApprovalHierarchyHistoryDao
				.addApprovalHierarchyHistory(approvalHierarchyHistory);

	}

}
