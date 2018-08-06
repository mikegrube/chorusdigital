package com.camas.chorusdigital.season;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by mike on 8/24/17.
 */
@Entity
public final class Season {
	private static final Logger log = LoggerFactory.getLogger(Season.class);

	@Id
	@GeneratedValue
	private Long id;

	@Size(min = 4, max = 4, message = "Start year must be 4 digits")
	private String startYear = "";

	private String blurb;

	private String brochureFront;

	private String brochureBack;

	@Size(max=255,message="Notes must be 255 characters or fewer")
	private String notes;

	public Season() {
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

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getBlurb() {
		return blurb;
	}

	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}

	public String getBrochureFront() {
		return brochureFront;
	}

	public void setBrochureFront(String brochureFront) {
		this.brochureFront = brochureFront;
	}

	public String getBrochureBack() {
		return brochureBack;
	}

	public void setBrochureBack(String brochureBack) {
		this.brochureBack = brochureBack;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProgramPath() {
		return "/files/brochures/" + getStartYear() + "-Brochure.pdf";
	}

	public String toString() {
		final Integer endYear = Integer.parseInt(startYear) + 1;
		return "Season " + startYear + "-" + endYear;
	}

}
