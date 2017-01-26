package com.ibcs.desco.cs.service;

import java.util.List;

import com.ibcs.desco.cs.model.CSProcItemRcvDtl;

public interface CSProcItemRcvDtlService {
	
	public void addCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl);
	
	public void removeCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl);

	public void editCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl);

	public CSProcItemRcvDtl getCSProcItemRcvDtl(int id);

	public List<CSProcItemRcvDtl> getAllCSProcItemRcvDtl();

	public List<CSProcItemRcvDtl> getCSProcItemRcvDtlByRrNo(String rrNo);

	public CSProcItemRcvDtl getCSProcItemRcvDtlByContratNo(String contractNo, String itemId);
}
