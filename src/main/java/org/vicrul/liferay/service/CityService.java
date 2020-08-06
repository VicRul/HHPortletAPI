package org.vicrul.liferay.service;

import java.util.List;

import org.vicrul.liferay.model.City;

public interface CityService {
	
	List<City> getAllCities();
	void updateCityList(long regionId);
}
