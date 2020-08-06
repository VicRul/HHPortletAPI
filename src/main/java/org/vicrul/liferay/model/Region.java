package org.vicrul.liferay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Region")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {
	
	@Id
	@Column(name = "region_id")
	private long regionId;
	
	@Column(name = "region_name")
	private String regionName;
}
