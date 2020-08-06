package org.vicrul.liferay.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vicrul.liferay.model.Vacancy;

import lombok.NoArgsConstructor;

@Repository
@Transactional
@NoArgsConstructor
public class VacancyDAOImpl implements VacancyDAO {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public Session getCurrentSession() {
		return sessionFactory.openSession();
	}
	
	@Transactional
	@Override
	public List<Vacancy> getAllVacancies() {		
		Session session = getCurrentSession();
		List<Vacancy> allVacancies = new ArrayList<Vacancy>();

		try {
			session.beginTransaction();
			allVacancies = session.createQuery("from Vacancy").list();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return allVacancies;
	}

	@Transactional
	@Override
	public void saveVacancy(List<Vacancy> allVacancies) {
		Session session = getCurrentSession();
		
		try {
			session.beginTransaction();
			for (Vacancy vacancy : allVacancies) {
				session.save(vacancy);
			}
			session.getTransaction().commit();
		} finally {
			session.close();
		}		
	}

	@Transactional
	@Override
	public void removeAllVacancies() {
		Session session = getCurrentSession();

		try {
			session.beginTransaction();
			session.createQuery("DELETE FROM Vacancy v").executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Transactional
	@Override
	public Vacancy findVacancy(long vacancyId) {
		Session session = getCurrentSession();
		Vacancy vacancy = null;
		
		try {
			session.beginTransaction();
			vacancy = (Vacancy) session.get(Vacancy.class, vacancyId);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return vacancy;
	}

}
