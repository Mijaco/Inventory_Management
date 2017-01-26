package com.ibcs.desco.workshop.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.TransformerTestApprovalHierarchyHistory;
import com.ibcs.desco.contractor.model.Contractor;
import com.ibcs.desco.inventory.constants.ContentType;
import com.ibcs.desco.workshop.model.ManufactureNames;
import com.ibcs.desco.workshop.model.TransformerCloseOutDtl;
import com.ibcs.desco.workshop.model.TransformerCloseOutMst;
import com.ibcs.desco.workshop.model.TransformerRegister;
import com.ibcs.desco.workshop.model.WsAsBuiltDtl;
import com.ibcs.desco.workshop.model.WsInventoryLookUp;

@Repository
@Transactional
public class WSTransformerDaoImpl implements WSTransformerDao {

	private static final Logger logger = LoggerFactory
			.getLogger(WSTransformerDaoImpl.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransformerRegister> getTransformerListBySLNoAndManufactureNameAndYear(
			String transformerSLNo, String manufacturedName,
			String manufacturedYear) {
		List<TransformerRegister> transformerRegisterList;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from TransformerRegister where transformerSerialNo='"
							+ transformerSLNo
							+ "' and manufacturedName='"
							+ manufacturedName
							+ "' and manufacturedYear='"
							+ manufacturedYear
							+ "' and testDate is not null order by testDate desc");

			transformerRegisterList = query.list();

			return transformerRegisterList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransformerRegister> getTransformerListWithNullTestDateAndValidJobNo(
			String contractNo) {
		List<TransformerRegister> transformerRegisterList;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from TransformerRegister where contractNo='"
							+ contractNo
							+ "' and jobNo is not null and testDate is null");

			transformerRegisterList = query.list();

			return transformerRegisterList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransformerRegister> getRegisteredTransformerListBeforeReturn() {

		try {
			Session session = sessionFactory.getCurrentSession();
			
			/*Query query = session
					.createQuery("select reqNo from TransformerRegister where returnSlipNo is null group by reqNo");*/
			Query query = session
					.createQuery("select distinct reqNo, createdDate from TransformerRegister order by createdDate desc");
			
			List<Object[]> list = query.list();
			List<TransformerRegister> transformerRegisterList = new ArrayList<TransformerRegister>();
			
			TransformerRegister tr = null;

			for (Object object : list) {
				Object[] li = (Object[]) object;
				tr = new TransformerRegister();
				tr.setReqNo(li[0].toString());
				transformerRegisterList.add(tr);
			}
			return transformerRegisterList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransformerRegister> getRegisteredTransformerListForReturn(
			List<String> itemCodes, String contractNo) {

		try {
			String itemCode = "";
			int i = 1;
			for (String j : itemCodes) {
				itemCode += "'" + j + "'";
				if (i < itemCodes.size()) {
					itemCode += ",";
				}
				i++;
			}

			Session session = sessionFactory.getCurrentSession();
			/*
			 * Query query = session .createQuery(
			 * "from TransformerRegister where returnSlipNo is null group by reqNo"
			 * );
			 */
			Query query = null;
			if (itemCodes.size() > 1) {
				query = session
						.createQuery("select transformerSerialNo, jobNo from TransformerRegister where itemCode in ("
								+ itemCode
								+ ") and contractNo ='"+contractNo+"' and transformerSerialNo is not null and testDate is not null and returnSlipNo is null");
			} else {
				query = session
						.createQuery("select transformerSerialNo, jobNo from TransformerRegister where itemCode ="
								+ itemCode
								+ " and contractNo ='"+contractNo+"' and transformerSerialNo is not null and testDate is not null and returnSlipNo is null");
			}

			List<Object[]> list = query.list();
			List<TransformerRegister> transformerRegisterList = new ArrayList<TransformerRegister>();

			TransformerRegister tr = null;

			for (Object object : list) {
				Object[] li = (Object[]) object;
				tr = new TransformerRegister();
				tr.setTransformerSerialNo(li[0].toString());
				tr.setJobNo(li[1].toString());
				transformerRegisterList.add(tr);
			}
			return transformerRegisterList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransformerRegister> getRegisteredTransformerListByReqNo(
			String requisitionNo) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from TransformerRegister where reqNo='"
							+ requisitionNo + "'");

			List<TransformerRegister> transformerRegisterList = query.list();

			return transformerRegisterList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TransformerRegister> getObjectListByAnyColumnWithNotNullDate(
			String columnName, String columnValue, String column2) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from TransformerRegister where "
					+ columnName + " = '" + columnValue + "' and " + column2
					+ " is not null");
			List<TransformerRegister> transformerList = query.list();
			return transformerList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransformerTestApprovalHierarchyHistory> getApprovalHierarchyHistoryByOpNameOpIdAndStatus(
			String operationName, String operationId, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(TransformerTestApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("operationName", operationName));
		criteria.add(Restrictions.eq("operationId", operationId));
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("stateCode"));

		List<TransformerTestApprovalHierarchyHistory> csitemList = criteria
				.list();
		return csitemList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransformerRegister getRegisteredTransformer(String transformerSlNo) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from TransformerRegister where transformerSerialNo='"
							+ transformerSlNo + "'");

			List<TransformerRegister> transformerRegisterList = query.list();

			if (transformerRegisterList.size() > 0) {
				return transformerRegisterList.get(0);
			} else {
				return null;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Long getCount(String contractNo, String xFormerType, String repairWork){
		/*try {*/
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("select count(*) from TransformerRegister where contractNo='"
							+ contractNo + "' and transformerType='"
							+ xFormerType + "' and typeOfWork='"
							+ repairWork + "'");

			String count = query.list().get(0).toString();
			Long l=Long.valueOf(count);
			/*if(count.size()>0){
				l= count.get(0);
			}*/
			
				return l;
			
	}

	@Override
	public Object getObjectListbyThreeColumnAndOneNotNullColumn(
			String objectName, String columnName1, String columnValue1,
			String columnName2, String columnValue2, String columnName3,
			String columnValue3, String notNullColumnName) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName1 + " = '" + columnValue1 + "' and "
					+ columnName2 + " = '" + columnValue2 + "' and "
					+ columnName3 + " = '" + columnValue3 + "' and "
					+ notNullColumnName + " is not null");
			Object objectList = query.list();
			return objectList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Object getObjectListbyFourColumnAndOneNotNullColumn(
			String objectName, String columnName1, String columnValue1,
			String columnName2, String columnValue2, String columnName3,
			String columnValue3, String columnName4,
			String columnValue4, String notNullColumnName) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName1 + " = '" + columnValue1 + "' and "
					+ columnName2 + " = '" + columnValue2 + "' and "
					+ columnName3 + " = '" + columnValue3 + "' and "
					+ columnName4 + " = '" + columnValue4 + "' and "
					+ notNullColumnName + " is not null");
			Object objectList = query.list();
			return objectList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransformerRegister> getTransformerListForGatePass() {

		try {

			Session session = sessionFactory.getCurrentSession();
			
			Query query = session
						.createQuery("from TransformerRegister where testDate is not null and jobNo is not null and returnTicketNo is null and typeOfWork='"+ContentType.PREVENTIVE_MAINTENANCE.toString()+"'");
			
			List<TransformerRegister> transformerRegisterList = query.list();

			return transformerRegisterList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WsInventoryLookUp> getWsInventoryLookUpItemList() {
	Session session = this.sessionFactory.getCurrentSession();
	Criteria c = session.createCriteria(WsInventoryLookUp.class);
	//c.addOrder(Order.asc("itemName"));
	List<WsInventoryLookUp> wsInventoryLookUpItemList = c.list();
	
	return wsInventoryLookUpItemList;

	}// nasrin
	
	//nasrin
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TransformerRegister> getTransformerListForInventoryReport() {
		
		try{
		Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from TransformerRegister where transformerSerialNo is not null and jobNo is null");
			List<TransformerRegister> transformerRegisterList = query.list();
			
			return transformerRegisterList;
	} catch (Exception e) {
		logger.error(e.getMessage());
		return null;
	}
	}
	
	//nasrin
	
		@SuppressWarnings("unchecked")
		@Override
		public List<Contractor> getContractorList(String type) {
			
			try{
			Session session = sessionFactory.getCurrentSession();
				Query query = session
						.createQuery("from Contractor where contractorType='"+type+"'");
				List<Contractor> con = query.list();
				
				return con;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		}
		
		/*@SuppressWarnings("unchecked")
		@Override
		
		List<JobCardTemplate> getJobLookupItemList() {
			
			try{
			Session session = sessionFactory.getCurrentSession();
				Query query = session
						.createQuery("from JobCardTemplate");
				List<JobCardTemplate> con = query.list();
				
				return con;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		}*/
		@Override
		@SuppressWarnings("unchecked")
		public List<ManufactureNames> getAllName() {
			Session session = this.sessionFactory.getCurrentSession();
			Criteria c = session.createCriteria(ManufactureNames.class);
			c.addOrder(Order.asc("manufactureName"));
			List<ManufactureNames> categoryList = c.list();
			return categoryList;

		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<TransformerRegister> getReturnTransformerList(String contractNo, String startDate, String endDate) {
			//String[] d1=startDate.split(" ");
			//String[] d2=endDate.split(" ");
			try{
			Session session = sessionFactory.getCurrentSession();
				//Query query = session
					//	.createQuery("from TransformerRegister where contractNo='"+contractNo+"' and TO_CHAR(receivedDate, 'DD-MM-YYYY')>='"+ d1[0] + "' and TO_CHAR(receivedDate, 'DD-MM-YYYY')<='"+ d2[0] + "' and returnDate is not null");
			
			Query query = session
						.createQuery("from TransformerRegister where contractNo='"+contractNo+"' and receivedDate BETWEEN TO_DATE('"+ startDate + "', 'dd-MM-yyyy') and TO_DATE('"+ endDate + "', 'dd-MM-yyyy') and returnDate is not null and closeOut=false");
			List<TransformerRegister> con = query.list();
				
				return con;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public WsAsBuiltDtl getAsbuilt(String jobNo, String itemCode) {
			
			try{
			Session session = sessionFactory.getCurrentSession();
				Query query = session
						.createQuery("from WsAsBuiltDtl where asBuiltMst.jobNo='"+jobNo+"' and itemCode='"+itemCode+"'");
				List<WsAsBuiltDtl> con = query.list();
				if(con.size()>0){
				return con.get(0);
				}else {return null;}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		}
		
	@Override
		public boolean isExist(String objectName, String columnName, String columnValue) {
			Query query =null;
			try {
				Session session = sessionFactory.getCurrentSession();
				 query = session.createQuery("from " + objectName + " where "
						+ columnName + " = '" + columnValue + "'");				

			} catch (Exception e) {
				logger.error(e.getMessage());
				
			}
			if(query.list()!=null && query.list().size()>0){
				
				return true;
			}else{
				return false;}
		}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<WsAsBuiltDtl> getToBeReturnMaterialList(String contractNo, String typeOfWork){
		
		try{
			Session session = sessionFactory.getCurrentSession();
				Query query = session
						.createQuery("select DISTINCT w.itemCode, w.itemName from WsAsBuiltDtl w where w.asBuiltMst.woNumber='"+contractNo+"' and w.materialsInHand > 0 and w.asBuiltMst.jobNo in(select jobCardNo from JobCardMst where typeOfWork='"+typeOfWork+"')");
				//List<WsAsBuiltDtl> con = query.list();
				List<WsAsBuiltDtl> dmds = new ArrayList<WsAsBuiltDtl>();
				WsAsBuiltDtl dmd=null;
				List<Object[]> list = query.list();
				for (Object object : list) {
					dmd = new WsAsBuiltDtl();
					Object[] li = (Object[]) object;
					dmd.setItemCode(li[0].toString()); // property
																			// id
				    dmd.setItemName(li[1].toString());
					dmds.add(dmd);
				}
				
				
				return dmds;
				
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public
	List<TransformerCloseOutMst> getTransformerCloseOutList(){
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		return (List<TransformerCloseOutMst>) session.createCriteria(
				TransformerCloseOutMst.class).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public
	List<TransformerCloseOutDtl> getTransformerCloseOutDetails(String mstId){
		
		try{
			Session session = sessionFactory.getCurrentSession();
				Query query = session
						.createQuery("from TransformerCloseOutDtl where transformerCloseOutMst.id="+mstId);
				List<TransformerCloseOutDtl> con = query.list();
				return con;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
	}

}
