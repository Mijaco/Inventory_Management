package com.ibcs.desco.subStore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.subStore.model.SSItemTransactionMst;

@Repository
@Transactional
public class SSItemTransactionMstDaoImpl implements SSItemTransactionMstDao {
	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SSItemTransactionMst getSSItemTransactionMst(String itemCode, Integer khathId, String ledgerName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(
					"from SSItemTransactionMst mst where mst.itemCode = :itemCode and mst.khathId = :khathId and mst.ledgerName = :ledgerName order by mst.id desc");
			query.setString("itemCode", itemCode);
			query.setInteger("khathId", khathId);
			query.setString("ledgerName", ledgerName);
			List<SSItemTransactionMst> ssMst = query.list();
			if (ssMst.size() > 0) {
				return (SSItemTransactionMst) query.list().get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
