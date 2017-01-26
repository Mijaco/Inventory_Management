package com.ibcs.desco.workshop.Dao;

import java.util.List;

import com.ibcs.desco.common.model.TransformerTestApprovalHierarchyHistory;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.workshop.model.ManufactureNames;
import com.ibcs.desco.workshop.model.TransformerCloseOutDtl;
import com.ibcs.desco.workshop.model.TransformerCloseOutMst;
import com.ibcs.desco.workshop.model.TransformerRegister;
import com.ibcs.desco.workshop.model.WsAsBuiltDtl;
import com.ibcs.desco.workshop.model.WsInventoryLookUp;

public interface WSTransformerDao {

	List<TransformerRegister> getTransformerListBySLNoAndManufactureNameAndYear(
			String transformerSLNo, String manufacturedName,
			String manufacturedYear);

	List<TransformerRegister> getTransformerListWithNullTestDateAndValidJobNo(
			String contractNo);

	List<TransformerRegister> getRegisteredTransformerListBeforeReturn();//nasrin
	
	List<TransformerRegister> getRegisteredTransformerListForReturn(List<String> itemCodes, String contractNo);//nasrin
	
	List<TransformerRegister> getRegisteredTransformerListByReqNo(String requisitionNo);//nasrin
	
	List<TransformerRegister> getObjectListByAnyColumnWithNotNullDate( String columnName, String columnValue, String column2 );

	List<TransformerTestApprovalHierarchyHistory> getApprovalHierarchyHistoryByOpNameOpIdAndStatus(
			String operationName, String operationId, String status);
	
	TransformerRegister getRegisteredTransformer(String transformerSlNo);//nasrin
	
	Long getCount(String contractNo, String xFormerType, String repairWork);//nasrin
	
	Object getObjectListbyThreeColumnAndOneNotNullColumn(
			String objectName, String columnName1, String columnValue1,
			String columnName2, String columnValue2, String columnName3,
			String columnValue3, String notNullColumnName);
	
	List<TransformerRegister> getTransformerListForGatePass();//nasrin

	Object getObjectListbyFourColumnAndOneNotNullColumn(String objectName,
			String columnName1, String columnValue1, String columnName2,
			String columnValue2, String columnName3, String columnValue3,
			String columnName4, String columnValue4, String notNullColumnName);
	
	List<WsInventoryLookUp> getWsInventoryLookUpItemList();//nasrin
	
	List<TransformerRegister> getTransformerListForInventoryReport();//nasrin
	
	List<Contractor> getContractorList(String type);//nasrin
	
	/*List<JobCardTemplate> getJobLookupItemList();*/
	List<ManufactureNames> getAllName();
	
	List<TransformerRegister> getReturnTransformerList(String contractNo, String startDate, String endDate);
	
	WsAsBuiltDtl getAsbuilt(String jobNo, String itemCode);
	
	boolean isExist(String objectName, String columnName, String columnValue);
	
	List<WsAsBuiltDtl> getToBeReturnMaterialList(String contractNo, String typeOfWork);
	
	List<TransformerCloseOutMst> getTransformerCloseOutList();
	
	List<TransformerCloseOutDtl> getTransformerCloseOutDetails(String mstId);

}
