package com.camas.chorusdigital.gallery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/* This class represents a single photo in the gallery

 */
@Entity
public class Photo {
	private static final Logger log = LoggerFactory.getLogger(Photo.class);

	@Id
	@GeneratedValue
	private Long id;

	private String photoLocation;
	private String caption;
	private String year;
	private String event;
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhotoLocation() {
		return photoLocation;
	}

	public void setPhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
