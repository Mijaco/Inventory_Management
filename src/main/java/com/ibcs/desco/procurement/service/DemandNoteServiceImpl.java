package com.ibcs.desco.procurement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.procurement.dao.DemandNoteDAO;
import com.ibcs.desco.procurement.model.DemandNoteDtl;
@Service
public class DemandNoteServiceImpl implements 
	DemandNoteService	{
	DemandNoteDAO demandNoteDao;

	public DemandNoteDAO getDemandNoteDao() {
		return demandNoteDao;
	}

	public void setDemandNoteDao(DemandNoteDAO demandNoteDao) {
		this.demandNoteDao = demandNoteDao;
	}
	
	@Override
	public List<DemandNoteDtl> getDemandNoteSummarybyMstIdColumnValueList( List<String> columnValues) {
		// TODO Auto-generated method stub
		return demandNoteDao.getDemandNoteSummarybyMstIdColumnValueList(columnValues);
	}

	@Override
	public List<DemandNoteDtl> getDemandNoteCodedSummary(String sessionId,
			String itemType) {
		// TODO Auto-generated method stub
		return demandNoteDao.getDemandNoteCodedSummary(sessionId, itemType);
	}
}
