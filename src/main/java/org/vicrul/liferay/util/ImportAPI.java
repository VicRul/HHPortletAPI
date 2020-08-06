package org.vicrul.liferay.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.vicrul.liferay.model.Category;
import org.vicrul.liferay.model.City;
import org.vicrul.liferay.model.Region;
import org.vicrul.liferay.model.Vacancy;

import com.jayway.jsonpath.JsonPath;

public class ImportAPI {

	private String getJSON(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private int getRussiaId() {

		String url = "https://api.hh.ru/areas/countries";

		String russiaJson = JsonPath.read(getJSON(url), "$.[?(@.name == 'Россия')]").toString();
		int russiaId = Integer.parseInt(JsonPath.read(russiaJson, "$.[0].id").toString());

		return russiaId;
	}
	
	public List<Region> getRegions() {

		String result = getJSON("https://api.hh.ru/areas/" + getRussiaId());		
		int numberOfAreas = Integer.parseInt(JsonPath.read(result, "$.areas.length()").toString());
		
		List<Region> regionsList = new ArrayList<Region>();
		
		for (int i = 0; i < numberOfAreas; i++) {
			long id = Long.parseLong(JsonPath.read(result, "$.areas[" + i + "].id").toString());
			String name = JsonPath.read(result, "$.areas[" + i + "].name");
			
			regionsList.add(new Region(id, name));
		}

		return regionsList;
	}
	
	public List<City> getCities(long regionId) {

		String citiesInRegion = JsonPath.read(getJSON("https://api.hh.ru/areas/113"), "$.areas[?(@.id == '" + regionId + "')].areas[*]").toString();		
		int numberOfCitiesInRegion = JsonPath.read(citiesInRegion, "$.length()");
		
		List<City> cities = new ArrayList<City>();

		for (int i = 0; i < numberOfCitiesInRegion; i++) {
			long cityId = Long.parseLong(JsonPath.read(citiesInRegion, "$.[" + i + "].id").toString());
			String name = JsonPath.read(citiesInRegion, "$.[" + i + "].name");

			cities.add(new City(cityId, name));
		}
		
		return cities;
	}
	
	public List<Category> getCategories(){
		
		String result = getJSON("https://api.hh.ru/specializations");
		int numberOfCategories = Integer.parseInt(JsonPath.read(result, "$.length()").toString());
		
		List<Category> catigoriesList = new ArrayList<Category>();
		
		for (int i = 0; i < numberOfCategories; i++) {
			long id = Long.parseLong(JsonPath.read(result, "$.[" + i + "].id").toString());
			String name = JsonPath.read(result, "$.[" + i + "].name").toString();
			
			catigoriesList.add(new Category(id, name));
		}

		return catigoriesList;
	}
	
	public List<Vacancy> getVacancies(long cityId, long categoryId){
		
		List<String> jsons = new ArrayList<String>();

		for (int i = 0; i < 20; i++) {
			String stroke = getJSON("https://api.hh.ru/vacancies?specialization=" + categoryId + "&area=" + cityId + "&per_page=100&page=" + i);
			jsons.add(stroke);
		}
				
		List<Vacancy> allVacancies = new ArrayList<Vacancy>();
		
		for(int vacancy = 0, page = 0; vacancy < 100 && page < 20; vacancy++) {
			
			String json = JsonPath.read(jsons.get(page), "$..items[*]").toString();
			
			long id = Long.parseLong(JsonPath.read(json, "$.[" + vacancy + "].id").toString());
			String name = JsonPath.read(json, "$.[" + vacancy + "].name");
			Date published = Date.valueOf(LocalDate.parse(JsonPath.read(json, "$.[" + vacancy + "].published_at").toString().substring(0, 10), DateTimeFormatter.ofPattern("yyy-MM-dd")));
			String company = JsonPath.read(json, "$.[" + vacancy + "].employer.name");
			int salaryFrom = 0;
			int salaryTo = 0;
			String currency = "";
			
			Object salary = JsonPath.read(json, "$.[" + vacancy + "].salary");
			if(salary != null) {
				salaryFrom = JsonPath.read(json, "$.[" + vacancy + "].salary.from") != null ? (Integer)JsonPath.read(json, "$.[" + vacancy + "].salary.from") : 0 ;
				salaryTo =  JsonPath.read(json, "$.[" + vacancy + "].salary.to") == null ? 0 : (Integer)JsonPath.read(json, "$.[" + vacancy + "].salary.to");
				currency = JsonPath.read(json, "$.[" + vacancy + "].salary.currency");
			}
			
			allVacancies.add(new Vacancy(id, name, published, company, salaryFrom, salaryTo, currency));
			
			if (vacancy == 99) {
				vacancy = 0;
				page ++;
			}
		}
		
		
		return allVacancies;
	}
}
