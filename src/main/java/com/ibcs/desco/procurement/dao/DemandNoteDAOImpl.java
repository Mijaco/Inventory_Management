package com.ibcs.desco.procurement.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.dao.CommonDaoImpl;
import com.ibcs.desco.procurement.model.DemandNoteDtl;
@Repository
@Transactional
public class DemandNoteDAOImpl implements 
	DemandNoteDAO {
	private static final Logger logger = LoggerFactory
			.getLogger(CommonDaoImpl.class);
	
	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DemandNoteDtl> getDemandNoteCodedSummary(String sessionId, String itemType) {
		try {			
			Session session = sessionFactory.getCurrentSession();
			//String q="select item_master_id, sum(required_qty), avg(EST_unit_cost) , sum(EST_Total_cost) from DEMAND_NOTE_DTL where item_master_id in (select id from item_master where item_type='C') and GROUP BY item_master_id";
			Query query = session.createQuery("SELECT itemMaster.itemId, itemMaster.itemName,  itemMaster.unitCode, demandNoteMst.descoSession.sessionName  , SUM(requiredQunatity) as requiredQunatity, AVG(estimateUnitCost) as estimateUnitCost,  sum(previousYearConsumption) as previousYearConsumption  FROM DemandNoteDtl WHERE demandNoteMst.descoSession.id in("+ sessionId +") and itemMaster.itemType ='("+ itemType +")' GROUP BY itemMaster.id, itemMaster.itemId, itemMaster.itemName");
			List<DemandNoteDtl> demandNoteDtlList = (List<DemandNoteDtl>) query.list();
			
			return demandNoteDtlList;
			
		} catch( Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DemandNoteDtl> getDemandNoteSummarybyMstIdColumnValueList( List<String> columnValues) {
		try {
			String q = "";
			for (String s : columnValues) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("SELECT itemCode, itemName, AVG(estimateUnitCost) as estimateUnitCost, SUM(requiredQunatity) as requiredQunatity, unit, sum(previousYearConsumption) as previousYearConsumption, sum(existingQty) as existingQty FROM DemandNoteDtl WHERE demandNoteMst.id in("+ q +") GROUP BY itemCode,  itemName, unit");
			List<DemandNoteDtl> demandNoteDtlList = (List<DemandNoteDtl>) query.list();
			
			return demandNoteDtlList;
			
		} catch( Exception E) {
			logger.error(E.getMessage());
			return null;
		}
	}
}
