package org.vicrul.liferay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vicrul.liferay.dao.CategoryDAO;
import org.vicrul.liferay.dao.CityDAO;
import org.vicrul.liferay.dao.VacancyDAO;
import org.vicrul.liferay.model.Category;
import org.vicrul.liferay.model.City;
import org.vicrul.liferay.model.Vacancy;
import org.vicrul.liferay.util.ImportAPI;

import lombok.NoArgsConstructor;

@Transactional
@Service("VacancyService")
@NoArgsConstructor
public class VacancyServiceImpl implements VacancyService {

	@Autowired
	private VacancyDAO vacancyDao;

	@Autowired
	private CategoryDAO categoryDao;

	@Autowired
	private CityDAO cityDao;

	private ImportAPI importAPI;

	@Override
	public List<Vacancy> getAllVacancies() {
		return vacancyDao.getAllVacancies();
	}

	@Override
	public void updateVacancyList(long cityId, long categoryId) {

		if (!getAllVacancies().isEmpty()) {
			vacancyDao.removeAllVacancies();
		}
		
		City city = cityDao.findCity(cityId);
		Category category = categoryDao.findCategory(categoryId);

		importAPI = new ImportAPI();
		List<Vacancy> allVacancies = importAPI.getVacancies(cityId, categoryId);
		
		for (Vacancy vacancy : allVacancies) {
			vacancy.setCityId(city);
			vacancy.setCategoryId(category);
		}

		vacancyDao.saveVacancy(allVacancies);
	}

}
