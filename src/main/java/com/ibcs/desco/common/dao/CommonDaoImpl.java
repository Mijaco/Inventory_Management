package com.ibcs.desco.common.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.admin.model.DescoSession;
import com.ibcs.desco.common.model.CustomSequence;
import com.ibcs.desco.common.model.CustomSequence1;
import com.ibcs.desco.common.model.StoreLocations;

@Repository
@Transactional
public class CommonDaoImpl implements CommonDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CommonDaoImpl.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getMaxValueByObjectAndColumn(String objectName,
			String columnName) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select max(" + columnName
				+ ") from " + objectName);

		int max = 0;

		for (Iterator it = query.iterate(); it.hasNext();) {
			Object obj = it.next();
			if (obj != null) {
				max = (Integer) obj;

			}

			System.out.println("MAX ID : " + max);

		}

		return max;
	}

	@Override
	public void saveOrUpdateCustomSequenceToDB(String serial) {
		try {
			Integer maxId = Integer
					.parseInt(serial.substring(serial.length() - 4));
			CustomSequence customSequence = new CustomSequence();
			customSequence.setId(null);
			customSequence.setMaxId(maxId);
			customSequence.setSerial(serial);
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(customSequence);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void saveOrUpdateCustomSequence1ToDB(String serial) {
		try {
			Integer maxId = Integer
					.parseInt(serial.substring(serial.length() - 8));
			CustomSequence1 customSequence = new CustomSequence1();
			customSequence.setId(null);
			customSequence.setMaxId(maxId);
			customSequence.setSerial(serial);
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(customSequence);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void saveOrUpdateModelObjectToDB(Object object) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(object);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public Object getAnObjectByAnyUniqueColumn(String objectName,
			String columnName, String columnValue) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " = '" + columnValue + "'");

			return query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getHierarcyHistoryListByDeptIdAndRoleNameAndStatus(
			String objectName, String deptId, String roleName, String status) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName
					+ " where deptId = '" + deptId + "' and actRoleName = '"
					+ roleName + "' and status ='" + status + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getHierarcyHistoryListByDeptId_RoleName_TargetUserAndStatus(
			String objectName, String deptId, String roleName, String status, String targetUserId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName
					+ " where deptId = '" + deptId + "' and actRoleName = '"
					+ roleName + "' and status ='" + status + "' and targetUserId ='" + targetUserId + "'");

			return query.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getHierarcyHistoryListByOperationNameOperationIdAndStatus(
			String objectName, String operationName, String operationId,
			String status) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName
					+ " where operationName = '" + operationName
					+ "' and operationId = '" + operationId + "' and status ='"
					+ status + "' order by id ASC");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnValueList(String objectName,
			String columnName, List<String> columnValues) {
		try {
			String q = "";
			for (String s : columnValues) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " in (" + q + ") order by id desc");

			/*
			 * Criteria criteria = session.createCriteria(objectName);
			 * criteria.add(Restrictions.in(columnName, columnValues));
			 * List<Object> g=criteria.list();
			 */

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnValueListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1) {
		try {
			String q = "";
			for (String s : columnValues) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " in (" + q + ") and " + columnName1 + " = "
					+ columnValue1 + " order by id desc");

			/*
			 * Criteria criteria = session.createCriteria(objectName);
			 * criteria.add(Restrictions.in(columnName, columnValues));
			 * List<Object> g=criteria.list();
			 */

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnValueNotInListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1) {
		try {
			String q = "";
			for (String s : columnValues) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " not in (" + q + ") and " + columnName1 + " = "
					+ columnValue1 + " order by id desc");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumn(String objectName,
			String columnName, String columnValue) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " = '" + columnValue + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnOrderByAnyColumn(
			String objectName, String columnName, String columnValue,
			String orderBy, String orderedFormat) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " = '" + columnValue + "'" + " order by "
					+ orderBy + " " + orderedFormat);

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public String getCustomSequence(String prefix, String separator) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String month = String.valueOf((Calendar.getInstance().get(
				Calendar.MONTH) + 1));

		if (month.length() == 1) {
			month = "0" + month;
		}
		// month=(month.length()==1)? "0"+month:month;

		String format = prefix + separator + year + separator + month
				+ separator;

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select my_sequence('" + format + "') as num from dual")
				.addScalar("num", StandardBasicTypes.STRING);

		String serial = (String) query.uniqueResult();

		return serial;
	}
	
	@Override
	public Boolean savePhysicalInventotyByProcedure(String invDate) {		
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(
					"begin PHYSICAL_INVENTORY_PROCESS( '"+invDate+"' ); end;");
			
			query.executeUpdate();
			Boolean result = true;
			return result;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	@Override
	public Boolean saveUnserviceableItemsFromTnxDtlToAuction(String tnxDtlObject, String storeDeptId, String countDate) {		
		try{
			/*Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(
					"begin PHYSICAL_INVENTORY_PROCESS( '"+countDate+"' ); end;");
			
			query.executeUpdate();*/
			Boolean result = true;
			return result;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getCustomSequence1(String prefix, String separator) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String month = String.valueOf((Calendar.getInstance().get(
				Calendar.MONTH) + 1));

		if (month.length() == 1) {
			month = "0" + month;
		}
		// month=(month.length()==1)? "0"+month:month;

		String format = prefix + separator + year + separator + month
				+ separator;

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select my_sequence1('" + format + "') as num from dual")
				.addScalar("num", StandardBasicTypes.STRING);

		String serial = (String) query.uniqueResult();

		return serial;
	}

	@Override
	public String getOperationIdByPrefixAndSequenceName(String prefix,
			String descoDeptCode, String separator, String seqName) {
		int year = Calendar.getInstance().get(Calendar.YEAR);

		String month = String.valueOf((Calendar.getInstance().get(
				Calendar.MONTH) + 1));

		if (month.length() == 1) {
			month = "0" + month;
		}

		String format = prefix + separator + descoDeptCode + separator + year
				+ separator + month + separator;

		Session session = sessionFactory.getCurrentSession();
		/*
		 * Query query = session.createSQLQuery( "select my_sequence1('" +
		 * format + "') as num from dual") .addScalar("num",
		 * StandardBasicTypes.STRING);
		 */
		Query query = session.createSQLQuery(
				"select " + seqName + ".nextval as num from dual").addScalar(
				"num", StandardBasicTypes.LONG);

		Long nextVal = ((Long) query.uniqueResult());
		String newFormat = String.format("%08d", nextVal);
		String formattedOperationId = format + newFormat;

		return formattedOperationId;
	}

	@Override
	public Long getNextVal() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select dept_id_seq.nextval as num from dual").addScalar("num",
				StandardBasicTypes.LONG);

		return ((Long) query.uniqueResult()).longValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getHierarcyHistoryListByDeptIdAndRoleNameAndStatusAndOptId(
			String objectName, String deptId, String roleName, String status,
			String operationId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName
					+ " where deptId = '" + deptId + "' and actRoleName = '"
					+ roleName + "' and status ='" + status
					+ "'and lower(operationId) like lower('%'||'" + operationId
					+ "'||'%')");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllObjectList(String objectName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {

			Query query = session.createQuery("from " + objectName);

			return query.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "'");
			List<Object> list=query.list();
			// return query.list();
			return list;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void deleteAnObjectById(String objectName, Integer id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Object object = getAnObjectByAnyUniqueColumn(objectName, "id", id
					+ "");
			session.delete(object);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByThreeColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String nullColumnName) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and "
					+ nullColumnName + " is null");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByThreeColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String column2, String columnValue2, String nullColumnName) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + nullColumnName
					+ " is null");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnWithOneNullAndOneNotNull(
			String objectName, String columnNameNull, String columnNameNotNull) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnNameNull + "  is null and " + columnNameNotNull
					+ " is not null");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getDistinctValueListByOneColumnNameAndValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select distinct ( "
					+ distinctColumnName + " ) from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' ");
			List<Object> objList = query.list();
			return objList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getDistinctValueListByColumnNameAndNullValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1, String nullColumnName) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select distinct ( "
					+ distinctColumnName + " ) from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and "
					+ nullColumnName + " is null");
			List<Object> objList = query.list();
			return objList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getObjectListByFourColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "' and " + column4 + " like '%"
					+ columnValue4 + "%'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getObjectListByFourColumnWithoutLike(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "' and " + column4 + "= '"
					+ columnValue4 + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getObjectListByFiveColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4, String column5,
			String columnValue5) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "' and " + column4 + " ='" + columnValue4
					+ "' and " + column5 + " ='" + columnValue5 + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public String getStoreLocationNamebyId(String id) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			StoreLocations obj = (StoreLocations) session.get(
					StoreLocations.class, Integer.parseInt(id));

			return obj.getName();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void deleteAnObjectListByAnyColumn(String objectName,
			String columnName, String columnValue) {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<Object> objectList = getObjectListByAnyColumn(objectName,
					columnName, columnValue);
			for (Object ob : objectList) {
				session.delete(ob);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByDateRange(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ dateColumnName + " BETWEEN  TO_DATE('" + fromDate
					+ "', 'yyyy/MM/dd') and TO_DATE('" + toDate
					+ "', 'yyyy/MM/dd') and " + column1 + " like '%"
					+ columnValue1 + "%'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByDateRangeAndTwoColumn(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1, String column2,
			String columnValue2) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ dateColumnName + " BETWEEN  TO_DATE('" + fromDate
					+ "', 'yyyy/MM/dd') and TO_DATE('" + toDate
					+ "', 'yyyy/MM/dd') and " + column1 + " like '%"
					+ columnValue1 + "%'and " + column2 + " like '%"
					+ columnValue2 + "%'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByDateRangeAndThreeColumn(
			String objectName, String dateColumnName, String fromDate,
			String toDate, String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ dateColumnName + " BETWEEN  TO_DATE('" + fromDate
					+ "', 'yyyy/MM/dd') and TO_DATE('" + toDate
					+ "', 'yyyy/MM/dd') and " + column1 + " like '%"
					+ columnValue1 + "%'and " + column2 + " like '%"
					+ columnValue2 + "%'and " + column3 + " like '%"
					+ columnValue3 + "%'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public DescoSession getCurrentDescoSession() {
		// TODO Auto-generated method stub
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String startDate = df.format(now);		
		String endDate = df.format(now);
		
		try {
			Session session = sessionFactory.getCurrentSession();
			/*Query query = session
					.createQuery("from DescoSession where startDate < sysdate and endDate > sysdate");*/
			//Query query = session.createQuery("from DescoSession where id=2");
			Query query = session.createQuery("from DescoSession where "
					+ "startDate > TO_DATE('" + startDate
					+ "', 'yyyy/MM/dd') and endDate < TO_DATE('"+ endDate
					+ "', 'yyyy/MM/dd')");
			DescoSession descoSession = (DescoSession) query.list().get(0);
			return descoSession;	

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getDistinctValueListByColumnName(String objectName,
			String distinctColumnName) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select distinct ( "
					+ distinctColumnName + " ) from " + objectName);
			
			List<Object> objList = query.list();
			return objList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public void deleteAnObjectByTwoColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2) {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<Object> object = getObjectListByTwoColumn(objectName, column1, columnValue1, column2, columnValue2);
			session.delete(object.get(0));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void deleteAnObjectByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3) {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<Object> object = getObjectListByThreeColumn(objectName, column1, columnValue1, column2, columnValue2, column3, columnValue3);
			session.delete(object.get(0));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
