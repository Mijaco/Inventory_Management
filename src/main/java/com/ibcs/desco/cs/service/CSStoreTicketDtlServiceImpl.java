package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CSStoreTicketDtlDao;
import com.ibcs.desco.cs.model.CSStoreTicketDtl;
import com.ibcs.desco.subStore.model.SSStoreTicketDtl;

@Service
public class CSStoreTicketDtlServiceImpl implements CSStoreTicketDtlService {

	CSStoreTicketDtlDao csStoreTicketDtlDao;

	public CSStoreTicketDtlDao getCsStoreTicketDtlDao() {
		return csStoreTicketDtlDao;
	}

	public void setCsStoreTicketDtlDao(CSStoreTicketDtlDao csStoreTicketDtlDao) {
		this.csStoreTicketDtlDao = csStoreTicketDtlDao;
	}

	@Override
	public void addCSStoreTicketDtl(CSStoreTicketDtl csCSStoreTicketDtl) {
		// TODO Auto-generated method stub
		csStoreTicketDtlDao.addCSStoreTicketDtl(csCSStoreTicketDtl);
	}

	@Override
	public void addSSStoreTicketDtl(SSStoreTicketDtl csCSStoreTicketDtl) {
		// TODO Auto-generated method stub
		csStoreTicketDtlDao.addSSStoreTicketDtl(csCSStoreTicketDtl);
	}

	@Override
	public void removeCSStoreTicketDtl(CSStoreTicketDtl csStoreTicketDtl) {
		// TODO Auto-generated method stub
		csStoreTicketDtlDao.removeCSStoreTicketDtl(csStoreTicketDtl);
	}

	@Override
	public void editCSStoreTicketDtl(CSStoreTicketDtl csStoreTicketDtl) {
		// TODO Auto-generated method stub
		csStoreTicketDtlDao.editCSStoreTicketDtl(csStoreTicketDtl);
	}

	@Override
	public CSStoreTicketDtl getCSStoreTicketDtl(int id) {
		// TODO Auto-generated method stub
		return csStoreTicketDtlDao.getCSStoreTicketDtl(id);
	}

	@Override
	public List<CSStoreTicketDtl> getAllCSStoreTicketDtl() {
		// TODO Auto-generated method stub
		return csStoreTicketDtlDao.getAllCSStoreTicketDtl();
	}

	@Override
	public List<CSStoreTicketDtl> getCSStoreTicketDtlByTicketNo(String ticketNo) {
		// TODO Auto-generated method stub
		return csStoreTicketDtlDao.getCSStoreTicketDtlByTicketNo(ticketNo);
	}

}
