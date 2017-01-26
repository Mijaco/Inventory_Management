package com.ibcs.desco.common.dao;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.common.model.KhathTransferApprovalHierarchyHistory;
import com.ibcs.desco.cs.model.KhathTransferMst;

@Repository
@Transactional
public class KhathTransferHierarchyHistoryDaoImpl implements
		KhathTransferHierarchyHistoryDao {

	private SessionFactory sessionFactory;

	// sessionFactory set from servlet-context.xml
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KhathTransferApprovalHierarchyHistory> getApprovalHierarchyHistoryByOppNameOppIdAndStatus(
			String operationName, String operationId, String status) {
		Session session = sessionFactory.getCurrentSession();
		// String optId = operationId+"";
		Criteria criteria = session
				.createCriteria(KhathTransferApprovalHierarchyHistory.class);
		criteria.add(Restrictions.eq("operationName", operationName));
		criteria.add(Restrictions.eq("operationId", operationId));
		criteria.add(Restrictions.eq("status", status));
		// criteria.createAlias("stateCode", "stateCode");
		criteria.addOrder(Order.asc("stateCode"));

		List<KhathTransferApprovalHierarchyHistory> ktList = (List<KhathTransferApprovalHierarchyHistory>) criteria
				.list();
		return ktList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KhathTransferMst> getKhathTransferMstListByOperationIds(
			String[] operationIds) {

		Session session = sessionFactory.getCurrentSession();
		try {
			String[] a = Arrays.toString(operationIds).split("[\\[\\]]")[1]
					.split(", ");
			List<String> operation = Arrays.asList(a);
			List<KhathTransferMst> items1 = (List<KhathTransferMst>) session
					.createCriteria(KhathTransferMst.class)
					.add(Restrictions.in("transferNo", operation)).list();
			return items1;

		} catch (Exception e) {
			//logger.error(e.getMessage());
			return null;
		}
	}

}
