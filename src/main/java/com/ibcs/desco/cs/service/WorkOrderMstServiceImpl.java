package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.WorkOrderMstDao;
import com.ibcs.desco.cs.model.WorkOrderMst;

@Service
public class WorkOrderMstServiceImpl implements WorkOrderMstService {


	private WorkOrderMstDao workOrderMstDao;
	

	public WorkOrderMstDao getWorkOrderMstDao() {
		return workOrderMstDao;
	}


	public void setWorkOrderMstDao(WorkOrderMstDao workOrderMstDao) {
		this.workOrderMstDao = workOrderMstDao;
	}

	/*private CSProcItemRcvMstDao csProcItemRcvMstDao;

	public CSProcItemRcvMstDao getCsProcItemRcvMstDao() {
		return csProcItemRcvMstDao;
	}

	public void setCsProcItemRcvMstDao(CSProcItemRcvMstDao csProcItemRcvMstDao) {
		this.csProcItemRcvMstDao = csProcItemRcvMstDao;
	}

	@Override
	public void addCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst) {
		// TODO Auto-generated method stub
		csProcItemRcvMstDao.addCSProcItemRcvMst(csProcItemRcvMst);
	}

	@Override
	public void removeCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst) {
		// TODO Auto-generated method stub
		csProcItemRcvMstDao.removeCSProcItemRcvMst(csProcItemRcvMst);
	}

	@Override
	public void editCSProcItemRcvMst(CSProcItemRcvMst csProcItemRcvMst) {
		// TODO Auto-generated method stub
		csProcItemRcvMstDao.editCSProcItemRcvMst(csProcItemRcvMst);
	}

	@Override
	public CSProcItemRcvMst getCSProcItemRcvMst(int id) {
		// TODO Auto-generated method stub
		return csProcItemRcvMstDao.getCSProcItemRcvMst(id);
	}
@Override
	public List<CSProcItemRcvMst> listCSProcItemRcvMstByOperationIds(
			Integer[] operationId) {
		// TODO Auto-generated method stub
		return csProcItemRcvMstDao.listCSProcItemRcvMstByOperationIds(operationId);
	}
	@Override
	public List<CSProcItemRcvMst> getAllCSProcItemRcvMst() {
		// TODO Auto-generated method stub
		return csProcItemRcvMstDao.getAllCSProcItemRcvMst();
	}

	*/


	@Override
	public WorkOrderMst getWorkOrderMstByWorkOrderNo(String rrNo) {
		// TODO Auto-generated method stub
		return workOrderMstDao.getWorkOrderMstByWorkOrderNo(rrNo);
	}

	@Override
	public List<WorkOrderMst> getWorkOrderMstList() {
		// TODO Auto-generated method stub
		return workOrderMstDao.getWorkOrderMstList();
	}

}
