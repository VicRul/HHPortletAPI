package org.vicrul.liferay.dao;

import java.util.List;

import org.vicrul.liferay.model.Vacancy;

public interface VacancyDAO {

	List<Vacancy> getAllVacancies();
	void saveVacancy(List<Vacancy> allVacancies);
	void removeAllVacancies();
	Vacancy findVacancy(long vacancyId);
}
