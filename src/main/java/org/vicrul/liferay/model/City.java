package org.vicrul.liferay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "City")
@Data
@NoArgsConstructor
public class City {
	
	@Id
	@Column(name = "city_id")
	private long cityId;
	
	@Column(name = "city_name")
	private String cityName;

	@ManyToOne
	@JoinColumn(name = "region_id")
	private Region regionId;

	public City(long cityId, String cityName) {
		this.cityId = cityId;
		this.cityName = cityName;
	}
	
}
