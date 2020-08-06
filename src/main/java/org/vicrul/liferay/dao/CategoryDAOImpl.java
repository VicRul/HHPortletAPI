package org.vicrul.liferay.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vicrul.liferay.model.Category;

import lombok.NoArgsConstructor;

@Repository
@Transactional
@NoArgsConstructor
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Session getCurrentSession() {
		return sessionFactory.openSession();
	}

	@Transactional
	@Override
	public List<Category> getAllCategories() {
		Session session = getCurrentSession();
		List<Category> allCategories = new ArrayList<Category>();

		try {
			session.beginTransaction();
			allCategories = session.createQuery("from Category").list();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return allCategories;
	}

	@Transactional
	@Override
	public void saveCategory(List<Category> allCategories) {
		Session session = getCurrentSession();

		try {
			session.beginTransaction();
			for (Category category : allCategories) {
				session.save(category);
			}
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Transactional
	@Override
	public void removeAllCategories() {
		Session session = getCurrentSession();

		try {
			session.beginTransaction();
			session.createQuery("DELETE from Category C").executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Transactional
	@Override
	public Category findCategory(long categoryId) {
		Session session = getCurrentSession();
		Category category = null;
		
		try {
			session.beginTransaction();
			category = (Category) session.get(Category.class, categoryId);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return category;
	}

}
