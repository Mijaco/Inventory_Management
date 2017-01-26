package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.ReturnSlipMstDao;
import com.ibcs.desco.cs.model.ReturnSlipMst;

@Service
public class ReturnSlipMstServiceImpl implements ReturnSlipMstService {

	private ReturnSlipMstDao returnSlipMstDao;

	public ReturnSlipMstDao getReturnSlipMstDao() {
		return returnSlipMstDao;
	}

	public void setReturnSlipMstDao(ReturnSlipMstDao returnSlipMstDao) {
		this.returnSlipMstDao = returnSlipMstDao;
	}

	@Override
	public void addReturnSlipMst(ReturnSlipMst returnSlipMst) {
		// TODO Auto-generated method stub
		returnSlipMstDao.addReturnSlipMst(returnSlipMst);
	}

	@Override
	public void removeReturnSlipMst(int returnSlipMstId) {
		// TODO Auto-generated method stub
		returnSlipMstDao.removeReturnSlipMst(returnSlipMstId);
	}

	@Override
	public void editReturnSlipMst(ReturnSlipMst returnSlipMst) {
		// TODO Auto-generated method stub
		returnSlipMstDao.editReturnSlipMst(returnSlipMst);
	}

	@Override
	public ReturnSlipMst getReturnSlipMst(int id) {
		// TODO Auto-generated method stub
		return returnSlipMstDao.getReturnSlipMst(id);
	}

	@Override
	public List<ReturnSlipMst> getAllReturnSlipMst() {
		// TODO Auto-generated method stub
		return returnSlipMstDao.getAllReturnSlipMst();
	}

	@Override
	public ReturnSlipMst getReturnSlipMstByRSNo(String rsNo) {
		// TODO Auto-generated method stub
		return returnSlipMstDao.getReturnSlipMstByRSNo(rsNo);
	}

	@Override
	public List<ReturnSlipMst> listReturnSlipMstByOperationIds(
			String[] operationId) {
		// TODO Auto-generated method stub
		return returnSlipMstDao.listReturnSlipMstByOperationIds(operationId);
	}
	
	@Override
	public List<ReturnSlipMst> listReturnSlipMstByOperationIdList(
			List<String> operationId) {
		// TODO Auto-generated method stub
		return returnSlipMstDao.listReturnSlipMstByOperationIdList(operationId);
	}

}
