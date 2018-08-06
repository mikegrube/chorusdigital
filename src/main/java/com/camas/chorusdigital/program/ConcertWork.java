package com.camas.chorusdigital.program;

import com.camas.chorusdigital.concert.Concert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
public class ConcertWork {
	private static final Logger log = LoggerFactory.getLogger(ConcertWork.class);

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Concert concert;
	@ManyToOne
	private Work work;
	private boolean performedByGroup = true;
	private int track;

	private String audioFileName;
	private String videoURL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public boolean isPerformedByGroup() {
		return performedByGroup;
	}

	public void setPerformedByGroup(boolean performedByGroup) {
		this.performedByGroup = performedByGroup;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public String getAudioFileName() {

		if (audioFileName == null) {
			audioFileName = Integer.toString(track) + " " + getWork().getTitle() + ".m4a";
		}

		return audioFileName;
	}

	public void setAudioFileName(String audioFileName) {
		this.audioFileName = audioFileName;
	}

	public boolean audioExists() {
		//TODO: We need to figure out how to get this for the work page
		return false;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getAudioPath() {

		//'files' is defined in StaticResourceConfiguration
		return "/files/audio/" + getConcert().getSeason().getStartYear() + "-" + getConcert().getTitle() + "/";

	}

	public boolean videoExists() {
		return videoURL != null && videoURL != "";
	}

	@Override
	public String toString() {
		return concert.getTitle() + " - " + work.getTitle();
	}

}
