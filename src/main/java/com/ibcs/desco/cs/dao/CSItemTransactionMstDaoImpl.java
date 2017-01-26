package com.ibcs.desco.cs.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.cs.model.CSItemTransactionMst;

@Repository
@Transactional
public class CSItemTransactionMstDaoImpl implements CSItemTransactionMstDao{
	// sessionFactory for get Current Session and comes form servlet-context.xml
	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public CSItemTransactionMst getCSItemTransectionMst(String itemCode, Integer khathId, String ledgerName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CSItemTransactionMst mst where mst.itemCode = :itemCode and mst.khathId = :khathId and mst.ledgerName = :ledgerName order by mst.id desc");
			query.setString("itemCode", itemCode);
			query.setInteger("khathId", khathId);
			query.setString("ledgerName", ledgerName);
			return (CSItemTransactionMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public CSItemTransactionMst getCSItemTransectionMstForss(String itemCode, Integer khathId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session
					.createQuery("from CSItemTransactionMst mst where mst.itemCode = :itemCode and mst.khathId = :khathId and ledgerName IS NOT 'UNSERVICABLE' order by mst.id desc");
			query.setString("itemCode", itemCode);
			query.setInteger("khathId", khathId);
			List<CSItemTransactionMst> cs= query.list();
			if(cs.size()>0){
			return (CSItemTransactionMst) query.list().get(0);
			}else{return null;}

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}

}
