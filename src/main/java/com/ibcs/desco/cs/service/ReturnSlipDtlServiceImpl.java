package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.ReturnSlipDtlDao;
import com.ibcs.desco.cs.model.ReturnSlipDtl;

@Service
public class ReturnSlipDtlServiceImpl implements ReturnSlipDtlService {

	private ReturnSlipDtlDao returnSlipDtlDao;

	public ReturnSlipDtlDao getReturnSlipDtlDao() {
		return returnSlipDtlDao;
	}

	public void setReturnSlipDtlDao(ReturnSlipDtlDao returnSlipDtlDao) {
		this.returnSlipDtlDao = returnSlipDtlDao;
	}

	@Override
	public void addReturnSlipDtl(ReturnSlipDtl returnSlipDtl) {
		// TODO Auto-generated method stub
		returnSlipDtlDao.addReturnSlipDtl(returnSlipDtl);
	}

	@Override
	public void removeReturnSlipDtl(int returnSlipDtlId) {
		// TODO Auto-generated method stub
		returnSlipDtlDao.removeReturnSlipDtl(returnSlipDtlId);
	}

	@Override
	public void editReturnSlipDtl(ReturnSlipDtl returnSlipDtl) {
		// TODO Auto-generated method stub
		returnSlipDtlDao.editReturnSlipDtl(returnSlipDtl);
	}

	@Override
	public ReturnSlipDtl getReturnSlipDtl(int id) {
		// TODO Auto-generated method stub
		return returnSlipDtlDao.getReturnSlipDtl(id);
	}

	@Override
	public List<ReturnSlipDtl> getAllReturnSlipDtl() {
		// TODO Auto-generated method stub
		return returnSlipDtlDao.getAllReturnSlipDtl();
	}

	@Override
	public List<ReturnSlipDtl> getAllReturnSlipDtlByMstId(int returnSlipMstId) {
		// TODO Auto-generated method stub
		return returnSlipDtlDao.getAllReturnSlipDtlByMstId(returnSlipMstId);
	}

}
