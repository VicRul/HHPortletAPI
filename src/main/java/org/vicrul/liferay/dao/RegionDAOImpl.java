package org.vicrul.liferay.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vicrul.liferay.model.Region;

import lombok.NoArgsConstructor;

@Repository
@Transactional
@NoArgsConstructor
public class RegionDAOImpl implements RegionDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public Session getCurrentSession() {
		return sessionFactory.openSession();
	}

	@Transactional
	@Override
	public List<Region> getAllRegions() {
		Session session = getCurrentSession();
		List<Region> allRegions = new ArrayList<Region>();

		try {
			session.beginTransaction();
			allRegions = session.createQuery("from Region").list();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return allRegions;
	}

	@Transactional
	@Override
	public void saveRegions(List<Region> allRegions) {
		Session session = getCurrentSession();
		
		try {
			session.beginTransaction();
			for (Region region : allRegions) {
				session.save(region);
			}
			session.getTransaction().commit();
		} finally {
			session.close();
		}	
	}

	@Transactional
	@Override
	public void removeAllRegions() {
		Session session = getCurrentSession();

		try {
			session.beginTransaction();
			session.createQuery("DELETE from Region R").executeUpdate();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Transactional
	@Override
	public Region findRegion(long regionId) {
		Session session = getCurrentSession();
		Region region = null;
		
		try {
			session.beginTransaction();
			region = (Region) session.get(Region.class, regionId);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return region;
	}

}
