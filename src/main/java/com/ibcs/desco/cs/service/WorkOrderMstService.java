package com.ibcs.desco.cs.service;

import java.util.List;

import com.ibcs.desco.cs.model.WorkOrderMst;

public interface WorkOrderMstService {
	
	/*public void addCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl);
	
	public void removeCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl);

	public void editCSProcItemRcvDtl(CSProcItemRcvDtl csProcItemRcvDtl);

	public CSProcItemRcvDtl getCSProcItemRcvDtl(int id);

	public List<CSProcItemRcvDtl> getAllCSProcItemRcvDtl();*/
	public List<WorkOrderMst> getWorkOrderMstList();

	public WorkOrderMst getWorkOrderMstByWorkOrderNo(String rrNo);
}
