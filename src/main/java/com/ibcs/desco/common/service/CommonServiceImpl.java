package com.ibcs.desco.common.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.ibcs.desco.admin.model.Departments;
import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.common.dao.CommonDao;
import com.ibcs.desco.common.model.Constrants;
import com.ibcs.desco.common.model.StoreLocations;

@Service
public class CommonServiceImpl implements CommonService {

	CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public String getAuthRoleName() {
		// TODO Auto-generated method stub
		String roleName = "";
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// auth.getAuthorities()
			Collection<? extends GrantedAuthority> auths = auth
					.getAuthorities();
			for (GrantedAuthority a : auths) {
				roleName = a.getAuthority();
				break;
			}
		}
		return roleName;
	}

	@Override
	public String getAuthUserName() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		return auth.getName();
	}
	
	@Override
	public String filterAmpersand(String stringData) {
		if (stringData.contains("&")) {
			stringData = stringData.replace("&", "'||'&'||'");
		}
		return stringData;
	}

	@Override
	public Object getMaxValueByObjectAndColumn(String objectName,
			String columnName) {
		// TODO Auto-generated method stub
		return commonDao.getMaxValueByObjectAndColumn(objectName, columnName);
	}

	@Override
	public void saveOrUpdateModelObjectToDB(Object object) {
		// TODO Auto-generated method stub
		commonDao.saveOrUpdateModelObjectToDB(object);
	}

	@Override
	public void deleteAnObjectById(String objectName, Integer id) {
		// TODO Auto-generated method stub
		commonDao.deleteAnObjectById(objectName, id);
	}

	@Override
	public Object getAnObjectByAnyUniqueColumn(String objectName,
			String columnName, String columnValue) {
		return commonDao.getAnObjectByAnyUniqueColumn(objectName, columnName,
				columnValue);
	}

	@Override
	public List<Object> getObjectListByAnyColumn(String objectName,
			String columnName, String columnValue) {
		return commonDao.getObjectListByAnyColumn(objectName, columnName,
				columnValue);
	}

	@Override
	public List<Object> getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
			String objectName, String deptId, String roleName, String status) {
		return commonDao.getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
				objectName, deptId, roleName, status);
	}
	
	@Override
	public List<Object> getHierarcyHistoryListByDeptId_RoleName_TargetUserAndStatus(
			String objectName, String deptId, String roleName, String status,
			String targetUserId){
		return commonDao.getHierarcyHistoryListByDeptId_RoleName_TargetUserAndStatus(objectName,
				deptId, roleName, status, targetUserId);
	}

	@Override
	public List<Object> getHierarcyHistoryListByOperationNameOperationIdAndStatus(
			String objectName, String operationName, String operationId,
			String status) {
		// TODO Auto-generated method stub
		return commonDao
				.getHierarcyHistoryListByOperationNameOperationIdAndStatus(
						objectName, operationName, operationId, status);
	}

	@Override
	public List<Object> getObjectListByAnyColumnValueList(String objectName,
			String columnName, List<String> columnValues) {
		return commonDao.getObjectListByAnyColumnValueList(objectName,
				columnName, columnValues);
	}

	@Override
	public List<Object> getObjectListByAnyColumnValueListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1) {
		return commonDao
				.getObjectListByAnyColumnValueListAndOneColumn(objectName,
						columnName, columnValues, columnName1, columnValue1);
	}

	@Override
	public Long getNextVal() {
		return commonDao.getNextVal();
	}

	@Override
	public void saveOrUpdateCustomSequenceToDB(String serial) {
		commonDao.saveOrUpdateCustomSequenceToDB(serial);
	}

	@Override
	public String getCustomSequence(String prefix, String separator) {
		return commonDao.getCustomSequence(prefix, separator);
	}

	@Override
	public List<Object> getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
			String objectName, String deptId, String roleName, String status,
			String operationId) {
		// TODO Auto-generated method stub
		return commonDao
				.getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
						objectName, deptId, roleName, status, operationId);
	}

	@Override
	public String getCustomSequence1(String prefix, String separator) {
		// TODO Auto-generated method stub
		return commonDao.getCustomSequence1(prefix, separator);
	}

	@Override
	public void saveOrUpdateCustomSequence1ToDB(String serial) {
		commonDao.saveOrUpdateCustomSequence1ToDB(serial);
	}

	@Override
	public String getOperationIdByPrefixAndSequenceName(String prefix,
			String descoDeptCode, String separator, String seqName) {
		// TODO Auto-generated method stub
		return commonDao.getOperationIdByPrefixAndSequenceName(prefix,
				descoDeptCode, separator, seqName);
	}

	@Override
	public boolean isJSONValid(String JSON_STRING) {
		Gson gson = new Gson();
		try {
			gson.fromJson(JSON_STRING, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}

	@Override
	public List<Object> getAllObjectList(String objectName) {
		// TODO Auto-generated method stub
		return commonDao.getAllObjectList(objectName);
	}

	@Override
	public List<Object> getObjectListByTwoColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByTwoColumn(objectName, column1,
				columnValue1, column2, columnValue2);
	}

	@Override
	public List<Object> getObjectListByThreeColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByThreeColumn(objectName, column1,
				columnValue1, column2, columnValue2, column3, columnValue3);
	}

	@Override
	public List<Object> getObjectListByTwoColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String nullColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByTwoColumnWithOneNullValue(objectName,
				column1, columnValue1, nullColumnName);
	}

	@Override
	public List<Object> getObjectListByFourColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4) {
		return commonDao.getObjectListByFourColumn(objectName, column1,
				columnValue1, column2, columnValue2, column3, columnValue3,
				column4, columnValue4);
	}

	@Override
	public List<Object> getObjectListByFiveColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4, String column5,
			String columnValue5) {
		return commonDao.getObjectListByFiveColumn(objectName, column1,
				columnValue1, column2, columnValue2, column3, columnValue3,
				column4, columnValue4, column5, columnValue5);
	}

	@Override
	public String getStoreLocationNamebyId(String id) {
		return commonDao.getStoreLocationNamebyId(id);
	}

	@Override
	public void deleteAnObjectListByAnyColumn(String objectName,
			String columnName, String columnValue) {
		// TODO Auto-generated method stub
		commonDao.deleteAnObjectListByAnyColumn(objectName, columnName,
				columnValue);

	}

	@Override
	public String getLedgerListForGrid() {
		String ledgerBookList = "";
		for (Constrants.LedgerBook dir : Constrants.LedgerBook.values()) {
			if (dir.equals(Constrants.LedgerBook.AUCTION)) {
				continue;
			}
			ledgerBookList += dir + ":" + dir + ";";
		}
		ledgerBookList = ledgerBookList.substring(0,
				ledgerBookList.length() - 1);

		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		try {
			ledgerBookList = ow.writeValueAsString(ledgerBookList);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return ledgerBookList;
	}

	@Override
	public String getLocationListForGrid(List<StoreLocations> locationsList) {

		String locationOptions = "";
		String locationListString = "";
		for (int i = 0; i < locationsList.size(); i++) {
			locationOptions += locationsList.get(i).getId() + ":"
					+ locationsList.get(i).getName() + ";";
		}
		locationOptions = locationOptions.substring(0,
				locationOptions.length() - 1);

		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		try {
			locationListString = ow.writeValueAsString(locationOptions);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return locationListString;
	}

	@Override
	public List<Object> getObjectListByDateRange(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByDateRange(objectName, dateColumnName,
				fromDate, toDate, column1, columnValue1);
	}

	@Override
	public List<Object> getObjectListByDateRangeAndTwoColumn(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1, String column2,
			String columnValue2) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByDateRangeAndTwoColumn(objectName,
				dateColumnName, fromDate, toDate, column1, columnValue1,
				column2, columnValue2);
	}

	@Override
	public List<Object> getObjectListByDateRangeAndThreeColumn(
			String objectName, String dateColumnName, String fromDate,
			String toDate, String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByDateRangeAndThreeColumn(objectName,
				dateColumnName, fromDate, toDate, column1, columnValue1,
				column2, columnValue2, column3, columnValue3);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNewDeptIdByParentDept(String parentDepdtId) {
		List<Departments> deptListUnderContractors = (List<Departments>) (Object) commonDao
				.getObjectListByAnyColumn("Departments", "parent",
						parentDepdtId);
		Integer[] deptIds = new Integer[deptListUnderContractors.size()];
		for (int i = 0; i < deptListUnderContractors.size(); i++) {
			String dptId = deptListUnderContractors.get(i).getDeptId()
					.substring(parentDepdtId.length());
			deptIds[i] = Integer.parseInt(dptId);
		}
		Arrays.sort(deptIds, Collections.reverseOrder());
		String contractorDeptId = parentDepdtId;

		if (deptIds.length > 0) {
			contractorDeptId += String.format("%02d", deptIds[0] + 1);
		} else {
			contractorDeptId += "01";
		}

		return contractorDeptId;
	}

	@Override
	public DescoSession getCurrentDescoSession() {
		return commonDao.getCurrentDescoSession();
	}

	@Override
	public List<Object> getDistinctValueListByColumnName(String objectName,
			String distinctColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getDistinctValueListByColumnName(objectName,
				distinctColumnName);
	}

	@Override
	public List<Object> getObjectListByAnyColumnOrderByAnyColumn(
			String objectName, String columnName, String columnValue,
			String orderBy, String orderedFormat) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByAnyColumnOrderByAnyColumn(objectName,
				columnName, columnValue, orderBy, orderedFormat);
	}

	@Override
	public List<Object> getDistinctValueListByOneColumnNameAndValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1) {
		// TODO Auto-generated method stub
		return commonDao.getDistinctValueListByOneColumnNameAndValue(
				objectName, distinctColumnName, column1, columnValue1);
	}
	
	@Override
	public List<Object> getDistinctValueListByColumnNameAndNullValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1, String nullColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getDistinctValueListByColumnNameAndNullValue(
				objectName, distinctColumnName, column1, columnValue1,
				nullColumnName);
	}

	@Override
	public List<Object> getObjectListByThreeColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String column2, String columnValue2, String nullColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByThreeColumnWithOneNullValue(objectName, column1, columnValue1, column2, columnValue2, nullColumnName);
	}
	
	@Override
	public List<Object> getObjectListByTwoColumnWithOneNullAndOneNotNull(
			String objectName, String columnNameNull, String columnNameNotNull) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByTwoColumnWithOneNullAndOneNotNull(objectName, columnNameNull, columnNameNotNull);
	}

	@Override
	public List<Object> getObjectListByAnyColumnValueNotInListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByAnyColumnValueNotInListAndOneColumn(objectName, columnName, columnValues, columnName1, columnValue1);
	}
	
	@Override
	public Boolean savePhysicalInventotyByProcedure(String invDate){
		return commonDao.savePhysicalInventotyByProcedure(invDate);
	}
	
	@Override
	public Boolean saveUnserviceableItemsFromTnxDtlToAuction(String tnxDtlObject, String storeDeptId, String countDate){
		return commonDao.saveUnserviceableItemsFromTnxDtlToAuction(tnxDtlObject, storeDeptId, countDate);
	}
	
	@Override
	public String saveFileToDrive(MultipartFile file, String rootPath, String folderName) {
		File serverFile = null;
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();

			String extension = "";

			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = fileName.substring(i + 1);
			}

			if (extension.equalsIgnoreCase("pdf")) {
				String uniqueFileName = java.util.UUID.randomUUID().toString();
				try {
					byte[] bytes = file.getBytes();

					File dir = new File(rootPath + File.separator
							+ folderName);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					serverFile = new File(dir.getAbsolutePath()
							+ File.separator + uniqueFileName + "." + extension);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					// return serverFile.getAbsolutePath();
				} catch (Exception e) {
					return "You failed to upload " + uniqueFileName + " => "
							+ e.getMessage();
				}
				return serverFile.getAbsolutePath();
			}
		} else {
			return "";
		}
		return "";
	}
	
	@Override
	public void deleteAnObjectByTwoColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2) {
		commonDao.deleteAnObjectByTwoColumn(objectName, column1, columnValue1, column2, columnValue2);
	}
	@Override
	public void deleteAnObjectByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3) {
		commonDao.deleteAnObjectByThreeColumn(objectName, column1, columnValue1, column2, columnValue2, column3, columnValue3);
	}

	@Override
	public List<Object> getObjectListByFourColumnWithoutLike(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByFourColumnWithoutLike(objectName, column1,
				columnValue1, column2, columnValue2, column3, columnValue3,
				column4, columnValue4);
	}

}
