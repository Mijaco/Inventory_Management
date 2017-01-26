package com.ibcs.desco.cs.service;

import com.ibcs.desco.cs.model.CSItemLocationMst;

public interface CSItemLocationMstService {
	public CSItemLocationMst getCSItemLocationMst(String locationId,
			String ledgerName, String itemCode);

	public CSItemLocationMst getCSItemLocationMstBy4Param(String ledgerName,
			String itemCode, String locationId, String childLocationId);
}
