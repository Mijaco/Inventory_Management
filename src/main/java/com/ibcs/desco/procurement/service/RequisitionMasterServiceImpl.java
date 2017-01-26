package com.ibcs.desco.procurement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibcs.desco.procurement.dao.RequisitionMasterDAO;
import com.ibcs.desco.procurement.model.RequisitionMst;

@Service
public class RequisitionMasterServiceImpl implements RequisitionMasterService{
	
	private  RequisitionMasterDAO requisitionMasterDAO;
	

	public RequisitionMasterDAO getRequisitionMasterDAO() {
		return requisitionMasterDAO;
	}

	public void setRequisitionMasterDAO(RequisitionMasterDAO requisitionMasterDAO) {
		this.requisitionMasterDAO = requisitionMasterDAO;
	}

	@Override
	@Transactional
	public void addRequisitionMst(RequisitionMst requisitionMst) {
		// TODO Auto-generated method stub
		requisitionMasterDAO.addRequisitionMst(requisitionMst);
		
	}

	@Override
	@Transactional
	public void removeRequisitionMst(int requisitionMstId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void editRequisitionMst(RequisitionMst requisitionMst) {
		// TODO Auto-generated method stub
		requisitionMasterDAO.editRequisitionMst(requisitionMst);
	}

	@Override
	@Transactional
	public RequisitionMst getRequisitionMst(int id) {
		// TODO Auto-generated method stub
		return requisitionMasterDAO.getRequisitionMst(id);
	}

	@Override
	@Transactional
	public List<RequisitionMst> getAllRequisitionMst() {
		// TODO Auto-generated method stub
		return requisitionMasterDAO.getAllRequisitionMst();
	}

	@Override
	@Transactional
	public RequisitionMst getRequisitionMstByPRFNo(String prfNo) {
		// TODO Auto-generated method stub
		return requisitionMasterDAO.getRequisitionMstByPRFNo(prfNo);
	}

	@Override
	@Transactional
	public List<RequisitionMst> getRequisitionMstListByPRFNo(String prfNo) {
		// TODO Auto-generated method stub
		return requisitionMasterDAO.getRequisitionMstListByPRFNo(prfNo);
	}

	@Override
	@Transactional
	public RequisitionMst getRequisitionMstLastRowFromTab() {
		// TODO Auto-generated method stub
		return this.requisitionMasterDAO.getRequisitionMstLastRowFromTab();
	}
	
	@Override
	@Transactional
	public List<RequisitionMst> getRequisitionMstListByStatus(String status) {
		// TODO Auto-generated method stub
		return requisitionMasterDAO.getRequisitionMstListByStatus(status);
	}
}
