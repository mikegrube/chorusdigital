package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.season.Season;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Concert {
	private static final Logger log = LoggerFactory.getLogger(Concert.class);

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@NotNull(message="Season cannot be null")
	private Season season;

	@NotNull(message="Title cannot be null")
	private String title;
	@NotNull(message="Prefix cannot be null")
	@Size(min=3,max=3,message="Prefix must be 3 digits")
	private String prefix;
	@Size(max=255,message="Notes must be 255 characters or fewer")
	private String notes;

	private boolean active = false;

	private String videoURL;

	public Concert() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(String idString) {
		this.id = Long.parseLong(idString);
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrefix() { return prefix; }

	public void setPrefix(String prefix) { this.prefix = prefix; }

	public boolean isActive() {
		return active;
	}

	public String getActive() {
		return active ? "Active" : "Inactive";
	}

	public void setActive(boolean active) {

		this.active = active;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public boolean videoExists() {
		return videoURL != null && videoURL != "";
	}

	public String getProgramPath() {
		return "/files/programs/" + getSeason().getStartYear() + "-" + getTitle() + ".pdf";
	}

	public String toString() {
		return title;
	}
}