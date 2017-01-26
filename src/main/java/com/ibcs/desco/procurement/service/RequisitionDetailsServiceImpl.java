package com.ibcs.desco.procurement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibcs.desco.procurement.dao.RequisitionDetailsDAO;
import com.ibcs.desco.procurement.model.RequisitionDtl;
import com.ibcs.desco.procurement.model.RequisitionDtl2;


@Service
public class RequisitionDetailsServiceImpl implements RequisitionDetailsService{
	
	private  RequisitionDetailsDAO requisitionDetailsDAO;
	
	

	public RequisitionDetailsDAO getRequisitionDetailsDAO() {
		return requisitionDetailsDAO;
	}

	public void setRequisitionDetailsDAO(RequisitionDetailsDAO requisitionDetailsDAO) {
		this.requisitionDetailsDAO = requisitionDetailsDAO;
	}

	@Override
	@Transactional
	public void addRequisitionDetail(RequisitionDtl requisitionDtl) {
		// TODO Auto-generated method stub
		requisitionDetailsDAO.addRequisitionDetail(requisitionDtl);
		
	}
	
	@Override
	@Transactional
	public void removeRequisitionDetail(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void editRequisitionDetail(RequisitionDtl2 requisitionDtl) {
		requisitionDetailsDAO.editRequisitionDetail(requisitionDtl);
	}

	@Override
	@Transactional
	public RequisitionDtl getRequisitionDetail(int id) {
		// TODO Auto-generated method stub
		return requisitionDetailsDAO.getRequisitionDetail(id);
	}

	@Override
	@Transactional
	public List<RequisitionDtl> getAllRequisitionDetail(int RequisitionMstId) {
		// TODO Auto-generated method stub
		return requisitionDetailsDAO.getAllRequisitionDetail(RequisitionMstId);
	}

}
