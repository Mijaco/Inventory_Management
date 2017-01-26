package com.ibcs.desco.localStore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.localStore.model.LSItemTransactionMst;

@Repository
@Transactional
public class LSItemTransactionMstDaoImpl implements LSItemTransactionMstDao{
	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LSItemTransactionMst getLSItemTransectionMst(String itemCode, Integer khathId, String ledgerName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from LSItemTransactionMst mst where mst.itemCode = :itemCode and mst.khathId = :khathId and mst.ledgerName = :ledgerName order by mst.id desc");
			query.setString("itemCode", itemCode);
			query.setInteger("khathId", khathId);
			query.setString("ledgerName", ledgerName);
			
			List<LSItemTransactionMst> lsMst = query.list();
			
			if (lsMst.size() > 0) {
				return (LSItemTransactionMst) query.list().get(0);
			} else {
				return null;
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
