package com.camas.chorusdigital.program;

import java.util.ArrayList;
import java.util.List;

public class ConcertWorkDisplay {

	private Long id;
	private String title;
	private MusicContributor composer;
	private boolean performedByGroup;
	private String notes;
	private Long concertWorkId;
	private int track;
	private boolean audioExists;
	private boolean videoExists;

	List<ConcertWorkTrackDisplay> concertWorkTrackDisplays = new ArrayList<>();

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

	public MusicContributor getComposer() {
		return composer;
	}

	public void setComposer(MusicContributor composer) {
		this.composer = composer;
	}

	public boolean isPerformedByGroup() {
		return performedByGroup;
	}

	public void setPerformedByGroup(boolean performedByGroup) {
		this.performedByGroup = performedByGroup;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getConcertWorkId() {
		return concertWorkId;
	}

	public void setConcertWorkId(Long concertWorkId) {
		this.concertWorkId = concertWorkId;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public boolean isAudioExists() {
		return audioExists;
	}

	public void setAudioExists(boolean audioExists) {
		this.audioExists = audioExists;
	}

	public boolean isVideoExists() {
		return videoExists;
	}

	public void setVideoExists(boolean videoExists) {
		this.videoExists = videoExists;
	}

	public void addMovementDisplay(ConcertWorkTrackDisplay concertWorkTrackDisplay) {
		concertWorkTrackDisplays.add(concertWorkTrackDisplay);
	}

	public Iterable<ConcertWorkTrackDisplay> getConcertWorkTrackDisplays() {
		return concertWorkTrackDisplays;
	}

}
