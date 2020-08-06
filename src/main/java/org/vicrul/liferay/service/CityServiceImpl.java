package org.vicrul.liferay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vicrul.liferay.dao.CityDAO;
import org.vicrul.liferay.dao.RegionDAO;
import org.vicrul.liferay.model.City;
import org.vicrul.liferay.model.Region;
import org.vicrul.liferay.util.ImportAPI;

import lombok.NoArgsConstructor;

@Transactional
@Service("CityService")
@NoArgsConstructor
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDAO cityDao;
	
	@Autowired
	private RegionDAO regionDao;
	
	private ImportAPI importAPI;
	
	@Override
	public List<City> getAllCities() {
		return cityDao.getAllCities();
	}

	@Override
	public void updateCityList(long regionId) {
		
		if(!getAllCities().isEmpty()) {
			cityDao.removeAllCities();
		}
		
		importAPI = new ImportAPI();
		List<City> allCities = importAPI.getCities(regionId);
		Region region = regionDao.findRegion(regionId);
		
		for (City city : allCities) {
			city.setRegionId(region);
		}
		cityDao.saveCity(allCities);
	}
}
