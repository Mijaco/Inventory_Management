package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CSItemReceivedDao;
import com.ibcs.desco.cs.model.CSItemReceived;

@Service
public class CSItemReceivedServiceImpl implements CSItemReceivedService {

	CSItemReceivedDao csItemReceivedDao;

	public CSItemReceivedDao getCsItemReceivedDao() {
		return csItemReceivedDao;
	}

	public void setCsItemReceivedDao(CSItemReceivedDao csItemReceivedDao) {
		this.csItemReceivedDao = csItemReceivedDao;
	}

	@Override
	public void addCSItemReceived(CSItemReceived cSItemReceived) {
		// TODO Auto-generated method stub
		csItemReceivedDao.addCSItemReceived(cSItemReceived);
	}

	@Override
	public List<CSItemReceived> listCSItemReceiveds() {
		// TODO Auto-generated method stub
		return csItemReceivedDao.listCSItemReceiveds();
	}

	@Override
	public CSItemReceived getCSItemReceived(int id) {
		// TODO Auto-generated method stub
		return csItemReceivedDao.getCSItemReceived(id);
	}

	@Override
	public void deleteCSItemReceived(CSItemReceived cSItemReceived) {
		// TODO Auto-generated method stub
		csItemReceivedDao.deleteCSItemReceived(cSItemReceived);
	}

	@Override
	public List<CSItemReceived> listCSItemReceivedByReceivedReportNo(
			String receivedReportNo) {
		// TODO Auto-generated method stub
		return csItemReceivedDao
				.listCSItemReceivedByReceivedReportNo(receivedReportNo);
	}

	@Override
	public List<CSItemReceived> listCSItemReceivedByOperationIds(
			Integer[] operationId) {
		// TODO Auto-generated method stub
		return csItemReceivedDao.listCSItemReceivedByOperationIds(operationId);
	}

}
