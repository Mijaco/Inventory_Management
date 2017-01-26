package com.ibcs.desco.common.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.common.model.StoreLocations;

public interface CommonService {

	// This method will return maximum value of a column from given object or
	// table name

	public Object getMaxValueByObjectAndColumn(String objectName,
			String columnName);

	public void saveOrUpdateModelObjectToDB(Object object);

	public String getAuthRoleName();

	public String getAuthUserName();

	public Object getAnObjectByAnyUniqueColumn(String objectName, String columnName,
			String columnValue);

	public List<Object> getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
			String objectName, String deptId, String roleName, String status);

	public List<Object> getHierarcyHistoryListByOperationNameOperationIdAndStatus(
			String objectName, String operationName, String operationId,
			String status);

	public List<Object> getObjectListByAnyColumn(String objectName, String columnName,
			String columnValue);

	public List<Object> getObjectListByAnyColumnValueList(String objectName,
			String columnName, List<String> columnValues);

	public String getCustomSequence(String prefix, String separator);

	public Long getNextVal();

	public void saveOrUpdateCustomSequenceToDB(String serial);

	public List<Object> getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
			String objectName, String deptId, String roleName, String status,
			String operationId);
	
	public String getCustomSequence1(String prefix, String separator);

	public void saveOrUpdateCustomSequence1ToDB(String serial);
	
	public String getOperationIdByPrefixAndSequenceName(String prefix, String descoDeptCode, 
			String separator, String seqName);
	
	public List<Object> getAllObjectList(String objectName);

	boolean isJSONValid(String JSON_STRING);
	
	public List<Object> getObjectListByTwoColumn(
			String objectName, String column1, String columnValue1, String column2, String columnValue2);

	public void deleteAnObjectById(String objectName, Integer id);

	public List<Object> getObjectListByThreeColumn(String objectName, String column1,
			String columnValue1, String column2, String columnValue2,
			String column3, String columnValue3);

	public List<Object> getObjectListByTwoColumnWithOneNullValue(String objectName,
			String column1, String columnValue1, String nullColumnName);

	public List<Object> getObjectListByFourColumn(String objectName, String column1,
			String columnValue1, String column2, String columnValue2,
			String column3, String columnValue3, String column4, String columnValue4);

	public String getStoreLocationNamebyId(String id);
	
	public void deleteAnObjectListByAnyColumn(String objectName, String columnName,  String columnValue);

	public String getLocationListForGrid(List<StoreLocations> locationsList);
	
	public String getLedgerListForGrid();

	public List<Object> getObjectListByDateRange(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1);
	
	public List<Object> getObjectListByDateRangeAndTwoColumn(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1, String column2, String columnValue2);

	public List<Object> getObjectListByDateRangeAndThreeColumn(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3);

	public String getNewDeptIdByParentDept(String parentDepdtId);

	public List<Object> getObjectListByAnyColumnValueListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1);

	public DescoSession getCurrentDescoSession();

	public List<Object> getDistinctValueListByColumnName(String objectName,
			String distinctColumnName);
	
	public List<Object> getObjectListByAnyColumnOrderByAnyColumn(String objectName,
			String columnName, String columnValue, String orderBy,
			String orderedFormat);
	
	public List<Object> getDistinctValueListByColumnNameAndNullValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1, String nullColumnName);

	public List<Object> getObjectListByFiveColumn(String objectName, String column1,
			String columnValue1, String column2, String columnValue2,
			String column3, String columnValue3, String column4,
			String columnValue4, String column5, String columnValue5);
	
	public List<Object> getObjectListByThreeColumnWithOneNullValue(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String nullColumnName);

	public List<Object> getObjectListByTwoColumnWithOneNullAndOneNotNull(
			String objectName, String columnNameNull, String columnNameNotNull);
	
	public List<Object> getObjectListByAnyColumnValueNotInListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1);

	public Boolean savePhysicalInventotyByProcedure(String invDate);

	public List<Object> getDistinctValueListByOneColumnNameAndValue(String objectName,
			String distinctColumnName, String column1, String columnValue1);

	public Boolean saveUnserviceableItemsFromTnxDtlToAuction(String tnxDtlObject,
			String storeDeptId, String countDate);

	public String filterAmpersand(String stringData);

	public List<Object> getHierarcyHistoryListByDeptId_RoleName_TargetUserAndStatus(
			String objectName, String deptId, String roleName, String status,
			String targetUserId);

	public String saveFileToDrive(MultipartFile file, String rootPath,
			String folderName);
	
	public List<Object> getObjectListByFourColumnWithoutLike(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4);
	
	public void deleteAnObjectByTwoColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2);	
	public void deleteAnObjectByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3);	
}
