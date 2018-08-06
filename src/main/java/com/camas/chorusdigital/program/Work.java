package com.camas.chorusdigital.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Work {
	private static final Logger log = LoggerFactory.getLogger(Work.class);

	@Id
	@GeneratedValue
	private Long id;

	@NotNull(message="Title cannot be null")
	private String title;

	private String notes;
	private String tags;

	@Transient
	private MusicContributor composer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public MusicContributor getComposer() {
		return composer;
	}

	public void setComposer(MusicContributor composer) {
		this.composer = composer;
	}

	@Override
	public String toString() {
		return title;
	}

}
