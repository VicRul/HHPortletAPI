<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-1.8.3.js"></script>

<portlet:resourceURL id="updateRegionsList" var="updateRegionsListURL" />
<portlet:resourceURL id="updateCategoriesList" var="updateCategoriesListURL" />
<portlet:resourceURL id="updateCitiesList" var="updateCitiesListURL" />
<portlet:resourceURL id="vacanciesList" var="vacanciesListURL" />
</head>

<body>
	<h3>Вакансии:</h3>


	<div>
		<a href="#" onclick="updateRegionsList('${updateRegionsListURL}')"
			position="right">Обновить список регионов</a> <br /> 
			<a href="#"	onclick="updateCategoriesList('${updateCategoriesListURL}')">Обновить
			список специализаций</a>

	</div>
	<div>
		<label for="regions-selector">Выберите регион России: </label> 
		<select id="regions-selector" onchange="updateCitiesList(this.value, '${updateCitiesListURL}')">
			<c:forEach items="${regions}" var="region">
				<option value=${region.regionId}>${region.regionName}</option>
			</c:forEach>
		</select> 
		<label for="cities-selector">Выберите город в регионе: </label> 
		<select name="cities-selector" id="cities-selector">
			<c:forEach items="${cities}" var="city">
				<option value=${city.cityId}>${city.cityName}</option>
			</c:forEach>
		</select> 
		<label for="categories-selector">Выберите специализацию: </label> 
		<select name="categories-selector" id="categories-selector">
			<c:forEach items="${cateories}" var="category">
				<option value=${category.categoryId}>${category.categoryName}</option>
			</c:forEach>
		</select>
	</div>
	<button onclick="vacanciesList('${vacanciesListURL}')">Поиск</button>
	<br />

	<table id="vacancy-table" class="table table-striped table-bordered table-hover"
		border="1px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th width="10%">Id</th>
				<th width="30%">Name</th>
				<th width="10%">Published</th>
				<th width="20%">Company</th>
				<th width="5%">SalaryFrom</th>
				<th width="5%">SalaryTo</th>
				<th width="5%">Currency</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vacancies}" var="vacancy" begin="0" end="100">
				<tr>
					<td>${vacancy.vacancyId}</td>
					<td>${vacancy.vacancyName}</td>
					<td>${vacancy.published}</td>
					<td>${vacancy.company}</td>
					<td>${vacancy.salaryFrom}</td>
					<td>${vacancy.salaryTo}</td>
					<td>${vacancy.currency}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script>
		function vacanciesList(urlPath) {
			alert("HELLO");
			var city = document.getElementById("cities-selector").options.selectedIndex;
			var category = document.getElementById("categories-selector").options.selectedIndex;
			var cityId = document.getElementById("cities-selector").options[city].value;
			var categoryId = document.getElementById("categories-selector").options[category].value;
			$.ajax({
				type : "GET",
				data : "cityId="+cityId+"&categoryId="+categoryId,
				url : urlPath,
				success : function(response) {
					if (response != '') {
						var obj = JSON.parse(response);
						$("tbody").empty();
						$.each(obj, function(index, item) {
							$("tbody").append(
								'<tr>' +
									'<td>' + item.vacancyId + '</td>'+
									'<td>' + item.vacancyName + '</td>'+
									'<td>' + item.published + '</td>'+
									'<td>' + item.company + '</td>'+
									'<td>' + item.salaryFrom + '</td>'+
									'<td>' + item.salaryTo + '</td>'+
									'<td>' + item.currency + '</td>'+
								'</tr>'
								);
						});
					}
				}
			});
		}

		function updateRegionsList(urlPath) {
			$.ajax({
				type : "GET",
				url : urlPath,
				success : function(response) {
					if (response != '') {
						var obj = JSON.parse(response);
						$("#regions-selector").empty();
						$.each(obj, function(index, item) {
							$("#regions-selector").append(
									jQuery("<option />").attr("value",
											item.regionId)
											.text(item.regionName));
						});
					}
				}
			});
		}

		function updateCitiesList(regionId, urlPath) {
			var region = regionId;
			alert("regionId='" + region + "'");
			jQuery.ajax({
				type : "GET",
				data : "regionId=" + region,
				url : urlPath,
				success : function(response) {
					if (response != '') {
						var obj = JSON.parse(response);
						alert(response);
						jQuery("#cities-selector").empty();
						jQuery.each(obj, function(index, item) {
							jQuery("#cities-selector").append(
									jQuery("<option />").attr("value",
											item.cityId).text(item.cityName));
						});
					}
				}
			});
		}

		function updateCategoriesList(urlPath) {
			$.ajax({
				type : "GET",
				url : urlPath,
				success : function(response) {
					if (response != '') {
						var obj = JSON.parse(response);
						$("#categories-selector").empty();
						$.each(obj, function(index, item) {
							$("#categories-selector").append(
									jQuery("<option />").attr("value",
											item.categoryId).text(
											item.categoryName));
						});
					}
				}
			});
		}
	</script>
</body>
</html>