package org.vicrul.liferay.service;

import java.util.List;

import org.vicrul.liferay.model.Vacancy;

public interface VacancyService {

	public List<Vacancy> getAllVacancies();
	public void updateVacancyList(long cityId, long categoryId);
}
