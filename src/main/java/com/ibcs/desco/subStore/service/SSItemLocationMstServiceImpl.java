package com.ibcs.desco.subStore.service;

import org.springframework.stereotype.Service;
import com.ibcs.desco.subStore.dao.SSItemLocationMstDao;
import com.ibcs.desco.subStore.model.SSItemLocationMst;

@Service
public class SSItemLocationMstServiceImpl implements SSItemLocationMstService {
	SSItemLocationMstDao ssItemLocationMstDao;

	public SSItemLocationMstDao getSsItemLocationMstDao() {
		return ssItemLocationMstDao;
	}

	public void setSsItemLocationMstDao(
			SSItemLocationMstDao ssItemLocationMstDao) {
		this.ssItemLocationMstDao = ssItemLocationMstDao;
	}

	@Override
	public SSItemLocationMst getSSItemLocationMst(String locationId,
			String ledgerName, String itemCode) {
		// TODO Auto-generated method stub
		return ssItemLocationMstDao.getSSItemLocationMst(locationId,
				ledgerName, itemCode);
	}

}
