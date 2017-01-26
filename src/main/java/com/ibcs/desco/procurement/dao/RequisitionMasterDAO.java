package com.ibcs.desco.procurement.dao;

import java.util.List;

import com.ibcs.desco.procurement.model.RequisitionMst;

public interface RequisitionMasterDAO {

	public void addRequisitionMst(RequisitionMst requisitionMst);

	public void removeRequisitionMst(int requisitionMstId);

	public void editRequisitionMst(RequisitionMst requisitionMst);

	public RequisitionMst getRequisitionMst(int id);

	public List<RequisitionMst> getAllRequisitionMst();
	
	public RequisitionMst getRequisitionMstByPRFNo(String prfNo);
	
	public List<RequisitionMst> getRequisitionMstListByPRFNo(String prfNo);
	
	public RequisitionMst getRequisitionMstLastRowFromTab();
	
	public List<RequisitionMst> getRequisitionMstListByStatus(String status);

}
