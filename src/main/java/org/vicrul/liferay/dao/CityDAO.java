package org.vicrul.liferay.dao;

import java.util.List;

import org.vicrul.liferay.model.City;

public interface CityDAO {

	List<City> getAllCities();
	void saveCity(List<City> allCities);
	void removeAllCities();
	City findCity(long cityId);
}
