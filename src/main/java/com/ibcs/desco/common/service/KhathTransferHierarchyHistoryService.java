package com.ibcs.desco.common.service;

import java.util.List;

import com.ibcs.desco.common.model.KhathTransferApprovalHierarchyHistory;
import com.ibcs.desco.cs.model.KhathTransferMst;

public interface KhathTransferHierarchyHistoryService {
	public List<KhathTransferApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status);
	public List<KhathTransferMst> getKhathTransferMstListByOperationIds(String[] operationIds);
}
