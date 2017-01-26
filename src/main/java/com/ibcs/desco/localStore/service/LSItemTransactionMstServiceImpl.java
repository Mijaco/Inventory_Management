package com.ibcs.desco.localStore.service;

import org.springframework.stereotype.Service;

import com.ibcs.desco.localStore.dao.LSItemTransactionMstDao;
import com.ibcs.desco.localStore.model.LSItemTransactionMst;

@Service
public class LSItemTransactionMstServiceImpl implements
		LSItemTransactionMstService {

	LSItemTransactionMstDao lsItemTransactionMstDao;

	public LSItemTransactionMstDao getLsItemTransactionMstDao() {
		return lsItemTransactionMstDao;
	}

	public void setLsItemTransactionMstDao(
			LSItemTransactionMstDao lsItemTransactionMstDao) {
		this.lsItemTransactionMstDao = lsItemTransactionMstDao;
	}

	@Override
	public LSItemTransactionMst getLSItemTransectionMst(String itemCode,
			Integer khathId, String ledgerName) {
		// TODO Auto-generated method stub
		return lsItemTransactionMstDao.getLSItemTransectionMst(itemCode,
				khathId, ledgerName);
	}

}
