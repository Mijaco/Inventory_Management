package com.ibcs.desco.cs.dao;

import com.ibcs.desco.cs.model.CSItemLocationMst;

public interface CSItemLocationMstDao {
	public CSItemLocationMst getCSItemLocationMst(String locationId, String ledgerName, String itemCode);

	public CSItemLocationMst getCSItemLocationMstBy4Param(String ledgerName,
			String itemCode, String locationId, String childLocationId);
}
