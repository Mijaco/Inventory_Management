package com.ibcs.desco.subStore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.ibcs.desco.subStore.dao.SSReturnSlipMstDao;
import com.ibcs.desco.subStore.model.SSReturnSlipMst;

@Service
public class SSReturnSlipMstServiceImpl implements SSReturnSlipMstService {

	private SSReturnSlipMstDao ssReturnSlipMstDao;

	
	public SSReturnSlipMstDao getSsReturnSlipMstDao() {
		return ssReturnSlipMstDao;
	}

	public void setSsReturnSlipMstDao(SSReturnSlipMstDao ssReturnSlipMstDao) {
		this.ssReturnSlipMstDao = ssReturnSlipMstDao;
	}

	@Override
	public void addSSReturnSlipMst(SSReturnSlipMst returnSlipMst) {
		// TODO Auto-generated method stub
		ssReturnSlipMstDao.addSSReturnSlipMst(returnSlipMst);
	}

	@Override
	public void removeSSReturnSlipMst(int returnSlipMstId) {
		// TODO Auto-generated method stub
		ssReturnSlipMstDao.removeSSReturnSlipMst(returnSlipMstId);
	}

	@Override
	public void editSSReturnSlipMst(SSReturnSlipMst returnSlipMst) {
		// TODO Auto-generated method stub
		ssReturnSlipMstDao.editSSReturnSlipMst(returnSlipMst);
	}

	@Override
	public SSReturnSlipMst getSSReturnSlipMst(int id) {
		// TODO Auto-generated method stub
		return ssReturnSlipMstDao.getSSReturnSlipMst(id);
	}

	@Override
	public List<SSReturnSlipMst> getAllSSReturnSlipMst() {
		// TODO Auto-generated method stub
		return ssReturnSlipMstDao.getAllSSReturnSlipMst();
	}

	@Override
	public SSReturnSlipMst getSSReturnSlipMstByRSNo(String rsNo) {
		// TODO Auto-generated method stub
		return ssReturnSlipMstDao.getSSReturnSlipMstByRSNo(rsNo);
	}

	@Override
	public List<SSReturnSlipMst> listSSReturnSlipMstByOperationIds(
			String[] operationId) {
		// TODO Auto-generated method stub
		return ssReturnSlipMstDao.listSSReturnSlipMstByOperationIds(operationId);
	}
	
	@Override
	public List<SSReturnSlipMst> listSSReturnSlipMstByOperationIdList(
			List<String> operationId) {
		// TODO Auto-generated method stub
		return ssReturnSlipMstDao.listSSReturnSlipMstByOperationIdList(operationId);
	}

}
