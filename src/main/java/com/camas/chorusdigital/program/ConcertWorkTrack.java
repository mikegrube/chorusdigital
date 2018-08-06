package com.camas.chorusdigital.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.File;

@Entity
public class ConcertWorkTrack {
	private static final Logger log = LoggerFactory.getLogger(ConcertWorkTrack.class);

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private ConcertWork concertWork;

	private String title;
	private String notes;
	private int track;

	private String audioFileName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ConcertWork getConcertWork() {
		return concertWork;
	}

	public void setConcertWork(ConcertWork concertWork) {
		this.concertWork = concertWork;
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

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public String getAudioFileName() {

		if (audioFileName == null && track > 0 && title != null && title != "") {
			audioFileName = Integer.toString(track) + " " + getTitle() + ".m4a";
		}

		return audioFileName;
	}

	public void setAudioFileName(String audioFileName) {
		this.audioFileName = audioFileName;
	}

	public String getAudioPath() {
		return getConcertWork().getAudioPath() + getAudioFileName();
	}

	@Override
	public String toString() {
		return title;
	}
}
