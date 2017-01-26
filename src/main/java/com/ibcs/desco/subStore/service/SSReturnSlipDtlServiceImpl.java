package com.ibcs.desco.subStore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.subStore.dao.SSReturnSlipDtlDao;
import com.ibcs.desco.subStore.model.SSReturnSlipDtl;

@Service
public class SSReturnSlipDtlServiceImpl implements SSReturnSlipDtlService {

	private SSReturnSlipDtlDao ssReturnSlipDtlDao;
	
	
	public SSReturnSlipDtlDao getSsReturnSlipDtlDao() {
		return ssReturnSlipDtlDao;
	}
	public void setSsReturnSlipDtlDao(SSReturnSlipDtlDao ssReturnSlipDtlDao) {
		this.ssReturnSlipDtlDao = ssReturnSlipDtlDao;
	}
	@Override
	public void addSSReturnSlipDtl(SSReturnSlipDtl returnSlipDtl){
		
		ssReturnSlipDtlDao.addSSReturnSlipDtl(returnSlipDtl);
	}
	@Override
	public void removeSSReturnSlipDtl(int returnSlipDtlId) {
		// TODO Auto-generated method stub
		ssReturnSlipDtlDao.removeSSReturnSlipDtl(returnSlipDtlId);
	}

	@Override
	public void editSSReturnSlipDtl(SSReturnSlipDtl returnSlipDtl) {
		// TODO Auto-generated method stub
		ssReturnSlipDtlDao.editSSReturnSlipDtl(returnSlipDtl);
	}

	@Override
	public SSReturnSlipDtl getSSReturnSlipDtl(int id) {
		// TODO Auto-generated method stub
		return ssReturnSlipDtlDao.getSSReturnSlipDtl(id);
	}

	@Override
	public List<SSReturnSlipDtl> getAllSSReturnSlipDtl() {
		// TODO Auto-generated method stub
		return ssReturnSlipDtlDao.getAllSSReturnSlipDtl();
	}

	@Override
	public List<SSReturnSlipDtl> getAllSSReturnSlipDtlByMstId(int returnSlipMstId) {
		// TODO Auto-generated method stub
		return ssReturnSlipDtlDao.getAllSSReturnSlipDtlByMstId(returnSlipMstId);
	}

}
