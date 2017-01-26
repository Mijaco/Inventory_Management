package com.ibcs.desco.procurement.dao;

import java.util.List;

import com.ibcs.desco.procurement.model.RequisitionDtl;
import com.ibcs.desco.procurement.model.RequisitionDtl2;

public interface RequisitionDetailsDAO {

	public void addRequisitionDetail(RequisitionDtl requisitionDtl);

	public void removeRequisitionDetail(int id);

	public void editRequisitionDetail(RequisitionDtl2 requisitionDtl);

	public RequisitionDtl getRequisitionDetail(int id);

	public List<RequisitionDtl> getAllRequisitionDetail(int RequisitionMstId);

}
