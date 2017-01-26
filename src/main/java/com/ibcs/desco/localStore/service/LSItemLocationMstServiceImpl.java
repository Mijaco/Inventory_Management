package com.ibcs.desco.localStore.service;

import org.springframework.stereotype.Service;

import com.ibcs.desco.localStore.dao.LSItemLocationMstDao;
import com.ibcs.desco.localStore.model.LSItemLocationMst;

@Service
public class LSItemLocationMstServiceImpl implements LSItemLocationMstService {
	LSItemLocationMstDao lsItemLocationMstDao;

	public LSItemLocationMstDao getLsItemLocationMstDao() {
		return lsItemLocationMstDao;
	}

	public void setLsItemLocationMstDao(
			LSItemLocationMstDao lsItemLocationMstDao) {
		this.lsItemLocationMstDao = lsItemLocationMstDao;
	}

	@Override
	public LSItemLocationMst getLSItemLocationMst(String locationId,
			String ledgerName, String itemCode) {
		// TODO Auto-generated method stub
		return lsItemLocationMstDao.getLSItemLocationMst(locationId,
				ledgerName, itemCode);
	}

}
