package com.ibcs.desco.test.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.model.ItemMaster;

@Transactional
@Repository
public class ExceToDbDaoImpl implements ExceToDbDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveItemCategory(ItemCategory itemCategory) {
		System.out.println(itemCategory);
		sessionFactory.getCurrentSession().saveOrUpdate(itemCategory);

	}

	@Override
	public void saveItemMaster(ItemMaster itemMaster) {
		System.out.println(itemMaster);
		sessionFactory.getCurrentSession().saveOrUpdate(itemMaster);

	}

}
