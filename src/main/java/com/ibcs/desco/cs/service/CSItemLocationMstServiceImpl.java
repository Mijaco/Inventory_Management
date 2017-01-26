package com.ibcs.desco.cs.service;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CSItemLocationMstDao;
import com.ibcs.desco.cs.model.CSItemLocationMst;

@Service
public class CSItemLocationMstServiceImpl implements CSItemLocationMstService {
	CSItemLocationMstDao csItemLocationMstDao;

	public CSItemLocationMstDao getCsItemLocationMstDao() {
		return csItemLocationMstDao;
	}

	public void setCsItemLocationMstDao(
			CSItemLocationMstDao csItemLocationMstDao) {
		this.csItemLocationMstDao = csItemLocationMstDao;
	}

	@Override
	public CSItemLocationMst getCSItemLocationMst(String locationId,
			String ledgerName, String itemCode) {
		return csItemLocationMstDao.getCSItemLocationMst(locationId,
				ledgerName, itemCode);
	}

	@Override
	public CSItemLocationMst getCSItemLocationMstBy4Param(String ledgerName,
			String itemCode, String locationId, String childLocationId) {
		return csItemLocationMstDao.getCSItemLocationMstBy4Param(ledgerName,
				itemCode, locationId, childLocationId);
	}

}
