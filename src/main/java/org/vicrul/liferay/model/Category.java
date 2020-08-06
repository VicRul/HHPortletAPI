package org.vicrul.liferay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@Column(name = "category_id")
	private long categoryId;
	
	@Column(name = "category_name")
	private String categoryName;
}
