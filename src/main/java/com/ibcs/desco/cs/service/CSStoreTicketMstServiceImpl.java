package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.model.CSStoreTicketMst;
import com.ibcs.desco.subStore.model.SSStoreTicketMst;
import com.ibcs.desco.cs.dao.CSStoreTicketMstDao;

@Service
public class CSStoreTicketMstServiceImpl implements CSStoreTicketMstService {

	CSStoreTicketMstDao csStoreTicketMstDao;

	public CSStoreTicketMstDao getCsStoreTicketMstDao() {
		return csStoreTicketMstDao;
	}

	public void setCsStoreTicketMstDao(CSStoreTicketMstDao csStoreTicketMstDao) {
		this.csStoreTicketMstDao = csStoreTicketMstDao;
	}

	@Override
	public void addCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst) {
		// TODO Auto-generated method stub
		csStoreTicketMstDao.addCSStoreTicketMst(csStoreTicketMst);
	}

	@Override
	public void editCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst) {
		// TODO Auto-generated method stub
		csStoreTicketMstDao.editCSStoreTicketMst(csStoreTicketMst);
	}

	@Override
	public List<CSStoreTicketMst> listCSStoreTicketMsts() {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao.listCSStoreTicketMsts();
	}

	@Override
	public CSStoreTicketMst getCSStoreTicketMst(int id) {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao.getCSStoreTicketMst(id);
	}

	@Override
	public void deleteCSStoreTicketMst(CSStoreTicketMst csStoreTicketMst) {
		// TODO Auto-generated method stub
		csStoreTicketMstDao.deleteCSStoreTicketMst(csStoreTicketMst);
	}

	@Override
	public List<CSStoreTicketMst> listCSProcItemRcvMstByOperationIds(
			String [] operationId) {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao
				.listCSProcItemRcvMstByOperationIds(operationId);
	}
	
	@Override
	public List<CSStoreTicketMst> listCSProcItemRcvMstByOperationIdList(
			List<String> operationId) {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao
				.listCSProcItemRcvMstByOperationIdList(operationId);
	}

	@Override
	public CSStoreTicketMst getCSStoreTicketMstByTicketNo(String ticketNo) {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao.getCSStoreTicketMstByTicketNo(ticketNo);
	}

	@Override
	public SSStoreTicketMst getSSStoreTicketMstByTicketNo(String ticketNo) {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao.getSSStoreTicketMstByTicketNo(ticketNo);
	}

	@Override
	public List<String> getOperationIdListByDeptIdAndStatus(
			List<String> operationIdList, boolean flag, boolean approved) {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao.getOperationIdListByDeptIdAndStatus(operationIdList, flag, approved);
	}
	@Override
	public List<String> getOperationIdListByDeptIdAndStatusForSS(
			List<String> operationIdList, boolean flag, boolean approved) {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao.getOperationIdListByDeptIdAndStatusForSS(operationIdList, flag, approved);
	}

	@Override
	public List<CSStoreTicketMst> listCSStoreTicketMstifApproved(
			boolean approved) {
		// TODO Auto-generated method stub
		return csStoreTicketMstDao.listCSStoreTicketMstifApproved(approved);
	}
	@Override	
	public List<CSStoreTicketMst> csStoreTicketListForFixedAssets(){
		
		return csStoreTicketMstDao.csStoreTicketListForFixedAssets();
	}

	@Override	
	public List<SSStoreTicketMst> ssStoreTicketListForFixedAssets(){
		
		return csStoreTicketMstDao.ssStoreTicketListForFixedAssets();
	}

}
