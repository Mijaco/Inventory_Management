package com.ibcs.desco.cs.service;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CSItemTransactionMstDao;
import com.ibcs.desco.cs.model.CSItemTransactionMst;

@Service
public class CSItemTransactionMstServiceImpl implements
		CSItemTransactionMstService {
	
	CSItemTransactionMstDao csItemTransactionMstDao;

	public CSItemTransactionMstDao getCsItemTransactionMstDao() {
		return csItemTransactionMstDao;
	}

	public void setCsItemTransactionMstDao(
			CSItemTransactionMstDao csItemTransactionMstDao) {
		this.csItemTransactionMstDao = csItemTransactionMstDao;
	}

	@Override
	public CSItemTransactionMst getCSItemTransectionMst(String itemCode,
			 Integer khathId, String ledgerName) {
		// TODO Auto-generated method stub
		return csItemTransactionMstDao.getCSItemTransectionMst(itemCode,
				khathId, ledgerName);
	}
	@Override
	public CSItemTransactionMst getCSItemTransectionMstForss(String itemCode,
			 Integer khathId) {
		// TODO Auto-generated method stub
		return csItemTransactionMstDao.getCSItemTransectionMstForss(itemCode,
				khathId);
	}

}
