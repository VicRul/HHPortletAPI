package org.vicrul.liferay.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vicrul.liferay.model.City;

import lombok.NoArgsConstructor;

@Repository
@Transactional
@NoArgsConstructor
public class CityDAOImpl implements CityDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public Session getCurrentSession() {
		return sessionFactory.openSession();
	}

	@Transactional
	@Override
	public List<City> getAllCities() {
		Session session = getCurrentSession();
		List<City> allCities = new ArrayList<City>();

		try {
			session.beginTransaction();
			allCities = session.createQuery("from City").list();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return allCities;
	}

	@Transactional
	@Override
	public void saveCity(List<City> allCities) {
		Session session = getCurrentSession();
		
		try {
			session.beginTransaction();
			for (City city : allCities) {
				session.save(city);
			}	
			session.getTransaction().commit();
		} finally {
			session.close();
		}		
	}

	@Transactional
	@Override
	public void removeAllCities() {
		Session session = getCurrentSession();

		try {
			session.beginTransaction();
			session.createQuery("DELETE from City C").executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Transactional
	@Override
	public City findCity(long cityId) {
		Session session = getCurrentSession();
		City city = null;
		
		try {
			session.beginTransaction();
			city = (City) session.get(City.class, cityId);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return city;
	}

}
