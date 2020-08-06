package org.vicrul.liferay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.vicrul.liferay.service.CategoryService;
import org.vicrul.liferay.service.CityService;
import org.vicrul.liferay.service.RegionService;
import org.vicrul.liferay.service.VacancyService;
import org.vicrul.liferay.util.JsonServiceUtil;
import org.vicrul.liferay.model.Category;
import org.vicrul.liferay.model.City;
import org.vicrul.liferay.model.Region;
import org.vicrul.liferay.model.Vacancy;

@Controller
@RequestMapping("VIEW")
public class HomeController {

	@Autowired
	private RegionService regionService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private VacancyService vacancyService;
	
	@RenderMapping
	public String home(Model model) {

		List<Region> regions = regionService.getAllRegions();
		List<City> cities = cityService.getAllCities();
		List<Vacancy> vacancies = vacancyService.getAllVacancies();
		List<Category> categories = categoryService.getAllCategories();
		
		model.addAttribute("regions", regions);
		model.addAttribute("cities", cities);
		model.addAttribute("cateories", categories);
		model.addAttribute("vacancies", vacancies);
		
		return "home";
	}

	@ResourceMapping("updateRegionsList")
	public void updateRegionsList(ResourceResponse response) throws IOException {
		
		regionService.updateRegionList();
		PrintWriter writer = response.getWriter();
		List<Region> regions = regionService.getAllRegions();
		List<Object> regionsToJSON = new ArrayList<Object>();
		regionsToJSON.addAll(regions);
		JsonServiceUtil.writeJson(writer, regionsToJSON);
	}
	
	@ResourceMapping("updateCategoriesList")
	public void updateCategoriesList(ResourceResponse response) throws IOException {
		
		categoryService.updateCategoryList();
		PrintWriter writer = response.getWriter();
		List<Category> categories = categoryService.getAllCategories();
		List<Object> categoryToJSON = new ArrayList<Object>();
		categoryToJSON.addAll(categories);
		JsonServiceUtil.writeJson(writer, categoryToJSON);
	}
	
	@ResourceMapping("updateCitiesList")
	public void updateCitiesList(@RequestParam(value = "regionId", defaultValue = "4") Long regionId, 
			ResourceResponse response, 
			ResourceRequest request) throws IOException {

		cityService.updateCityList(regionId);
		PrintWriter writer = response.getWriter();
		List<City> cities = cityService.getAllCities();
		List<Object> citiesToJSON = new ArrayList<Object>();
		citiesToJSON.addAll(cities);
		JsonServiceUtil.writeJson(writer, citiesToJSON);
	}
	
	@ResourceMapping("vacanciesList")
	public void vacanciesList(@RequestParam(value = "cityId", defaultValue = "4") Long cityId, 
			@RequestParam(value = "categoryId", defaultValue = "1") Long categoryId,
			ResourceResponse response, 
			ResourceRequest request) throws IOException {
		
		vacancyService.updateVacancyList(cityId, categoryId);
		PrintWriter writer = response.getWriter();
		List<Vacancy> vacancies = vacancyService.getAllVacancies();
		List<Object> vacanciesToJSON = new ArrayList<Object>();
		vacanciesToJSON.addAll(vacancies);
		JsonServiceUtil.writeJson(writer, vacanciesToJSON);
	}
}
