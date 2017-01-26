package com.ibcs.desco.cs.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.CSItemLocationMst;

@Repository
@Transactional
public class CSItemLocationMstDaoImpl implements CSItemLocationMstDao {
	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public CSItemLocationMst getCSItemLocationMst(String locationId,
			String ledgerName, String itemCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CSItemLocationMst mst where mst.itemCode = :itemCode and mst.locationId = :locationId and mst.ledgerName = :ledgerName order by mst.id desc");
			query.setString("itemCode", itemCode);
			query.setString("locationId", locationId);
			query.setString("ledgerName", ledgerName);
			return (CSItemLocationMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	
	@Override
	public CSItemLocationMst getCSItemLocationMstBy4Param(String ledgerName, String itemCode, String locationId,String childLocationId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CSItemLocationMst mst where mst.itemCode = :itemCode and storeLocation.id = :locationId and mst.childLocationId = :childLocationId and mst.ledgerType = :ledgerName order by mst.id desc");
			query.setString("itemCode", itemCode);
			query.setString("locationId", locationId);
			query.setString("childLocationId", childLocationId);
			query.setString("ledgerName", ledgerName);
			return (CSItemLocationMst) query.list().get(0);
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
