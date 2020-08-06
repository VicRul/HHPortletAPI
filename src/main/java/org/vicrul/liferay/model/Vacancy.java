package org.vicrul.liferay.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Vacancy")
@Data
@NoArgsConstructor
public class Vacancy {
	
	@Id
	@Column(name = "vacancy_id")
	private long vacancyId;

	@Column(name = "name")
	private String vacancyName;

	@Column(name = "published")
	private Date published;

	@Column(name = "company")
	private String company;

	@Column(name = "salary_from")
	private int salaryFrom;

	@Column(name = "salary_to")
	private int salaryTo;

	@Column(name = "currency")
	private String currency;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category categoryId;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City cityId;

	public Vacancy(long vacancyId, String vacancyName, Date published, String company, int salaryFrom,
			int salaryTo, String currency) {
		this.vacancyId = vacancyId;
		this.vacancyName = vacancyName;
		this.published = published;
		this.company = company;
		this.salaryFrom = salaryFrom;
		this.salaryTo = salaryTo;
		this.currency = currency;
	}
	
}
