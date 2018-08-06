package com.camas.chorusdigital.program;

public class ConcertWorkTrackDisplay {

	private String title;
	private int track;
	private boolean audioExists;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
}
