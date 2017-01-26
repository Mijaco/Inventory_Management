package com.ibcs.desco.localStore.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.ibcs.desco.localStore.model.LSItemLocationMst;

@Repository
@Transactional
public class LSItemLocationMstDaoImpl implements LSItemLocationMstDao {
	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public LSItemLocationMst getLSItemLocationMst(String locationId,
			String ledgerName, String itemCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from LSItemLocationMst mst where mst.itemCode = :itemCode and mst.locationId = :locationId and mst.ledgerName = :ledgerName order by mst.id desc");
			query.setString("itemCode", itemCode);
			query.setString("locationId", locationId);
			query.setString("ledgerName", ledgerName);
			return (LSItemLocationMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
