package com.camas.chorusdigital.venue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Venue {
	private static final Logger log = LoggerFactory.getLogger(Venue.class);

	@Id
	@GeneratedValue
	private
	Long id;

	@NotNull(message="Name cannot be null")
	private String name;
	private String street;
	private String city;
	private Integer capacity;
	private String mapURL;

	public Venue() {

	}

	public Venue(String name, String street, String city, int capacity) {
		setName(name);
		setStreet(street);
		setCity(city);
		setCapacity(capacity);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(String idString) {
		this.id = Long.parseLong(idString);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public void setCapacity(String capacityString) {
		this.capacity = Integer.parseInt(capacityString);
	}

	public String getMapURL() {
		return mapURL;
	}

	public void setMapURL(String mapURL) {
		this.mapURL = mapURL;
	}

	public boolean mapExists() {
		return mapURL != null && mapURL != "";
	}

	@Override
	public String toString() {
		return name;
	}
}