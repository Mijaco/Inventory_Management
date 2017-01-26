package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CSProcItemRcvMstDao;
import com.ibcs.desco.cs.model.CSProcItemRcvMst;

@Service
public class CSProcItemRcvMstServiceImpl implements CSProcItemRcvMstService {

	private CSProcItemRcvMstDao csProcItemRcvMstDao;

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
	public List<CSProcItemRcvMst> getAllCSProcItemRcvMst() {
		// TODO Auto-generated method stub
		return csProcItemRcvMstDao.getAllCSProcItemRcvMst();
	}

	@Override
	public CSProcItemRcvMst getCSProcItemRcvMstByRrNo(String rrNo) {
		// TODO Auto-generated method stub
		return csProcItemRcvMstDao.getCSProcItemRcvMstByRrNo(rrNo);
	}

	@Override
	public List<CSProcItemRcvMst> listCSProcItemRcvMstByOperationIds(
			String[] operationId) {
		// TODO Auto-generated method stub
		return csProcItemRcvMstDao.listCSProcItemRcvMstByOperationIds(operationId);
	}

}
