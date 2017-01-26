package com.ibcs.desco.cs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.cs.dao.CSProcItemRcvDtlDao;
import com.ibcs.desco.cs.model.CSProcItemRcvDtl;

@Service
public class CSProcItemRcvDtlServiceImpl implements CSProcItemRcvDtlService {

	private CSProcItemRcvDtlDao csProcItemRcvDtlDao;

	public CSProcItemRcvDtlDao getCsProcItemRcvDtlDao() {
		return csProcItemRcvDtlDao;
	}

	public void setCsProcItemRcvDtlDao(CSProcItemRcvDtlDao csProcItemRcvDtlDao) {
		this.csProcItemRcvDtlDao = csProcItemRcvDtlDao;
	}

	@Override
	public void addCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl) {
		// TODO Auto-generated method stub
		csProcItemRcvDtlDao.addCSProcItemRcvDtl(csProcItemRcvDtl);
	}

	@Override
	public void removeCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl) {
		// TODO Auto-generated method stub
		csProcItemRcvDtlDao.removeCSProcItemRcvDtl(csProcItemRcvDtl);
	}

	@Override
	public void editCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl) {
		// TODO Auto-generated method stub
		csProcItemRcvDtlDao.editCSProcItemRcvDtl(csProcItemRcvDtl);
	}

	@Override
	public CSProcItemRcvDtl getCSProcItemRcvDtl(int id) {
		// TODO Auto-generated method stub
		return csProcItemRcvDtlDao.getCSProcItemRcvDtl(id);
	}

	@Override
	public List<CSProcItemRcvDtl> getAllCSProcItemRcvDtl() {
		// TODO Auto-generated method stub
		return csProcItemRcvDtlDao.getAllCSProcItemRcvDtl();
	}

	@Override
	public List<CSProcItemRcvDtl> getCSProcItemRcvDtlByRrNo(String rrNo) {
		// TODO Auto-generated method stub
		return csProcItemRcvDtlDao.getCSProcItemRcvDtlByRrNo(rrNo);
	}
	
	@Override
	public CSProcItemRcvDtl getCSProcItemRcvDtlByContratNo(String contractNo, String itemId) {
		// TODO Auto-generated method stub
		return csProcItemRcvDtlDao.getCSProcItemRcvDtlByContratNo(contractNo, itemId);
	}

}
