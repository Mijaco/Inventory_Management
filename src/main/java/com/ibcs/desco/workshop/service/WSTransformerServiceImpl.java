package com.ibcs.desco.workshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibcs.desco.common.model.TransformerTestApprovalHierarchyHistory;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.workshop.Dao.WSTransformerDao;
import com.ibcs.desco.workshop.model.ManufactureNames;
import com.ibcs.desco.workshop.model.TransformerCloseOutDtl;
import com.ibcs.desco.workshop.model.TransformerCloseOutMst;
import com.ibcs.desco.workshop.model.TransformerRegister;
import com.ibcs.desco.workshop.model.WsAsBuiltDtl;
import com.ibcs.desco.workshop.model.WsInventoryLookUp;

@Service
public class WSTransformerServiceImpl implements WSTransformerService {

	WSTransformerDao wSTransformerDao;

	public WSTransformerDao getwSTransformerDao() {
		return wSTransformerDao;
	}

	public void setwSTransformerDao(WSTransformerDao wSTransformerDao) {
		this.wSTransformerDao = wSTransformerDao;
	}

	@Override
	public List<TransformerRegister> getTransformerListBySLNoAndManufactureNameAndYear(
			String transformerSLNo, String manufacturedName,
			String manufacturedYear) {
		// TODO Auto-generated method stub
		return wSTransformerDao
				.getTransformerListBySLNoAndManufactureNameAndYear(
						transformerSLNo, manufacturedName, manufacturedYear);
	}

	@Override
	public List<TransformerRegister> getTransformerListWithNullTestDateAndValidJobNo(
			String contractNo) {
		return wSTransformerDao
				.getTransformerListWithNullTestDateAndValidJobNo(contractNo);

	}

	@Override
	public List<TransformerRegister> getRegisteredTransformerListBeforeReturn() {

		return wSTransformerDao.getRegisteredTransformerListBeforeReturn();

	}

	public List<TransformerRegister> getRegisteredTransformerListForReturn(
			List<String> itemCodes, String contractNo) {

		return wSTransformerDao
				.getRegisteredTransformerListForReturn(itemCodes, contractNo);

	}

	@Override
	public List<TransformerRegister> getRegisteredTransformerListByReqNo(
			String requisitionNo) {

		return wSTransformerDao
				.getRegisteredTransformerListByReqNo(requisitionNo);

	}

	@Override
	public List<TransformerRegister> getObjectListByAnyColumnWithNotNullDate(
			String columnName, String columnValue, String column2) {
		return wSTransformerDao.getObjectListByAnyColumnWithNotNullDate(
				columnName, columnValue, column2);
	}

	@Override
	public List<TransformerTestApprovalHierarchyHistory> getApprovalHierarchyHistoryByOpNameOpIdAndStatus(
			String operationName, String operationId, String status) {
		return wSTransformerDao
				.getApprovalHierarchyHistoryByOpNameOpIdAndStatus(
						operationName, operationId, status);
	}

	@Override
	public TransformerRegister getRegisteredTransformer(String transformerSlNo) {
		return wSTransformerDao.getRegisteredTransformer(transformerSlNo);

	}// nasrin

	@Override
	public Long getCount(String contractNo, String xFormerType,
			String repairWork) {
		return wSTransformerDao.getCount(contractNo, xFormerType, repairWork);

	}// nasrin

	// Added by Ashid
	@Override
	public Object getObjectListbyThreeColumnAndOneNotNullColumn(
			String objectName, String columnName1, String columnValue1,
			String columnName2, String columnValue2, String columnName3,
			String columnValue3, String notNullColumnName) {
		// TODO Auto-generated method stub
		return wSTransformerDao.getObjectListbyThreeColumnAndOneNotNullColumn(
				objectName, columnName1, columnValue1, columnName2,
				columnValue2, columnName3, columnValue3, notNullColumnName);
	}

	// Added by Ashid
	@Override
	public Object getObjectListbyFourColumnAndOneNotNullColumn(
			String objectName, String columnName1, String columnValue1,
			String columnName2, String columnValue2, String columnName3,
			String columnValue3, String columnName4, String columnValue4,
			String notNullColumnName) {
		// TODO Auto-generated method stub
		return wSTransformerDao.getObjectListbyFourColumnAndOneNotNullColumn(
				objectName, columnName1, columnValue1, columnName2,
				columnValue2, columnName3, columnValue3, columnName4,
				columnValue4, notNullColumnName);
	}

	@Override
	public List<TransformerRegister> getTransformerListForGatePass() {

		return wSTransformerDao.getTransformerListForGatePass();

	}// nasrin

	@Override
	public List<WsInventoryLookUp> getWsInventoryLookUpItemList() {

		return wSTransformerDao.getWsInventoryLookUpItemList();

	}// nasrin

	@Override
	public List<TransformerRegister> getTransformerListForInventoryReport() {

		return wSTransformerDao.getTransformerListForInventoryReport();

	}// nasrin
	
	@Override
	public List<Contractor> getContractorList(String type){

		return wSTransformerDao.getContractorList(type);

	}// nasrin
	
	/*@Override
	List<JobCardTemplate> getJobLookupItemList(){

		return wSTransformerDao.getJobLookupItemList();
	}*/// nasrin

	@Override
	public List<ManufactureNames> getAllName(){

		return wSTransformerDao.getAllName();

	}// nasrin

	
	@Override
	public List<TransformerRegister> getReturnTransformerList(String contractNo, String startDate, String endDate ){

		return wSTransformerDao.getReturnTransformerList(contractNo, startDate, endDate);

	}// nasrin
	
	@Override
	public WsAsBuiltDtl getAsbuilt(String jobNo, String itemCode){

		return wSTransformerDao.getAsbuilt(jobNo, itemCode);

	}// nasrin
	
	@Override
	public boolean isExist(String objectName, String columnName, String columnValue){

		return wSTransformerDao.isExist(objectName, columnName, columnValue);

	}// nasrin

	@Override
	public List<WsAsBuiltDtl> getToBeReturnMaterialList(String contractNo, String typeOfWork){

		return wSTransformerDao.getToBeReturnMaterialList(contractNo, typeOfWork);

	}// nasrin

	
	@Override
	public List<TransformerCloseOutMst> getTransformerCloseOutList(){

		return wSTransformerDao.getTransformerCloseOutList();
	}
	
	@Override
	public List<TransformerCloseOutDtl> getTransformerCloseOutDetails(String mstId){

		return wSTransformerDao.getTransformerCloseOutDetails(mstId);
	}
}
