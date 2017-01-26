package com.ibcs.desco.budget.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.budget.model.BudgetDtl;
import com.ibcs.desco.budget.model.BudgetMst;

@Repository
@Transactional
public class BudgetMstDaoImpl implements BudgetMstDao {
	private SessionFactory sessionFactory;
	

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	@Override
	public BudgetMst getOneByBudgetSession(Integer sessionId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(
					"from com.ibcs.desco.budget.model.BudgetMst bm where bm.descoSession.id = :id")
					.setParameter("id", sessionId);
			return (BudgetMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
		
	}


	@Override
	public BudgetMst getOneByBudgetSessionAndType(Integer sessionId, String budgetType) {
		System.out.println("sessionId "+sessionId +" budgetType "+budgetType);
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(
					"from com.ibcs.desco.budget.model.BudgetMst bm where bm.descoSession.id = :id and bm.budgetType = :budgetType")
					.setParameter("id", sessionId).setParameter("budgetType", budgetType);
			return (BudgetMst) query.list().get(0);

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
		
	}
	
	@Override
	public List<BudgetDtl> getValidBgtDtlListByMst(Integer budgetMstId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(
					"from com.ibcs.desco.budget.model.BudgetDtl bd "
					+ "where bd.budgetMst.id = :id and (bd.revBudget = false "
					+ "or (bd.revBudgetApproved = true))")
					.setParameter("id", budgetMstId);
			return query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
		
	}
	
	
	@Override
	public List<BudgetDtl> getReviceBgtDtlListByMst(Integer budgetMstId) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(
					"from com.ibcs.desco.budget.model.BudgetDtl bd "
					+ "where bd.budgetMst.id = :id and (bd.revBudget = true)")
					.setParameter("id", budgetMstId);
			return query.list();

		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
		
	}
}
