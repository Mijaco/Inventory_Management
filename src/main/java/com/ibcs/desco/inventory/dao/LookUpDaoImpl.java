package com.ibcs.desco.inventory.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.inventory.model.AllLookUp;

@Transactional
@Repository
public class LookUpDaoImpl implements LookUpDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addLokUp(AllLookUp lookup) {

		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(lookup);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AllLookUp> getAllLookUps() {

		return this.sessionFactory.getCurrentSession().createCriteria(AllLookUp.class).list();
	}

	@Override
	public AllLookUp getLookUps(int id) {

		return null;
	}

	@Override
	public void deleteLookUps() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getParentIdList() {

		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AllLookUp.class);
		return criteria.setProjection(Projections.property("parentId")).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getParentId(String title) {

		Integer parentId = 0;
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AllLookUp.class);
		criteria.add(Restrictions.eq("title", title));

		List<AllLookUp> lookUpList = criteria.list();
		parentId = lookUpList.get(0).getId();

		return parentId;
	}

	@Override
	public AllLookUp getLookUpById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		AllLookUp lookUp = (AllLookUp) session.get(AllLookUp.class, id);
		return lookUp;
	}

	@Override
	public void editLookUp(AllLookUp lookUp) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(lookUp);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AllLookUp> getLookUpByKeyword(String keyword) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.AllLookUp where lower(keyword) like lower(:keyword)");
			// query.setString("prfNo", prfNo);
			query.setParameter("keyword", "%" + keyword + "%");
			return query.list();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getParentIdByParentName(String parentName) {
		Integer parentId = 0;
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AllLookUp.class);
		criteria.add(Restrictions.eq("parentName", parentName));

		List<AllLookUp> lookUpList = criteria.list();
		parentId = lookUpList.get(0).getId();

		return parentId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AllLookUp> getAllLookupByParentname(String parentName) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.AllLookUp where lower(parentName) like lower(:parentName)");
			// query.setString("prfNo", prfNo);
			query.setParameter("parentName", "%" + parentName + "%");
			return query.list();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getParentIdByTitle(String title) {
		Integer parentId = 0;
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AllLookUp.class);
		criteria.add(Restrictions.eq("title", title));

		List<AllLookUp> lookUpList = criteria.list();
		parentId = lookUpList.get(0).getId();

		return parentId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AllLookUp> getAllLookUpParents() {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("from com.ibcs.desco.inventory.model.AllLookUp where parentId is null");			
			return query.list();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
