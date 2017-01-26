package com.ibcs.desco.cs.dao;

import java.util.List;

import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;

public interface CSStoreTicketMstDao {
	// data access for add new CSStoreTicketMst
	public void addCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst);

	// data access for add new CSStoreTicketMst
	public void editCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst);

	// data access for get all CSStoreTicketMst as List
	public List<CSStoreTicketMst> listCSStoreTicketMsts();

	// data access for get specific one CSStoreTicketMst information and update
	// CSStoreTicketMst info
	public CSStoreTicketMst getCSStoreTicketMst(int id);
	
	public CSStoreTicketMst getCSStoreTicketMstByTicketNo(String ticketNo);
	
	public SSStoreTicketMst getSSStoreTicketMstByTicketNo(String ticketNo);

	// data access for Delete an CSStoreTicketMst
	public void deleteCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst);
	
	public List<CSStoreTicketMst> listCSProcItemRcvMstByOperationIds(
			String [] operationId);

	List<CSStoreTicketMst> listCSProcItemRcvMstByOperationIdList(
			List<String> operationId);

	public List<String> getOperationIdListByDeptIdAndStatus(
			List<String> operationIdList, boolean flag, boolean approved);
	
	public List<String> getOperationIdListByDeptIdAndStatusForSS(
			List<String> operationIdList, boolean flag, boolean approved);
	
	public List<CSStoreTicketMst> listCSStoreTicketMstifApproved(boolean approved);	
	
	public List<CSStoreTicketMst> csStoreTicketListForFixedAssets();
	
	public List<SSStoreTicketMst> ssStoreTicketListForFixedAssets();
	
}
