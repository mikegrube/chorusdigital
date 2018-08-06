package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.season.Season;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class SeasonSalesClass {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@NotNull(message="Season cannot be null")
	private Season season;

	private String name;
	private double value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
