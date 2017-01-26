package com.ibcs.desco.subStore.service;

import org.springframework.stereotype.Service;

import com.ibcs.desco.subStore.dao.SSItemTransactionMstDao;
import com.ibcs.desco.subStore.model.SSItemTransactionMst;

@Service
public class SSItemTransactionMstServiceImpl implements SSItemTransactionMstService{
	SSItemTransactionMstDao ssItemTransactionMstDao;

	public SSItemTransactionMstDao getSsItemTransactionMstDao() {
		return ssItemTransactionMstDao;
	}

	public void setSsItemTransactionMstDao(
			SSItemTransactionMstDao ssItemTransactionMstDao) {
		this.ssItemTransactionMstDao = ssItemTransactionMstDao;
	}

	@Override
	public SSItemTransactionMst getSSItemTransactionMst(String itemCode,
			Integer khathId, String ledgerName) {
		// TODO Auto-generated method stub
		return ssItemTransactionMstDao.getSSItemTransactionMst(itemCode, khathId, ledgerName);
	}
	
}
