package com.camas.chorusdigital.reporting;

public class WorkInfo {

	private String name;
	private String composerName;
	private String arrangerName;

	private boolean composerExists = true;
	private boolean audioAvailable = false;
	private boolean multipleTracks = false;
	private boolean videoAvailable = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComposerName() {
		return composerName;
	}

	public void setComposerName(String composerName) {
		this.composerName = composerName;
	}

	public String getArrangerName() {
		return arrangerName;
	}

	public void setArrangerName(String arrangerName) {
		this.arrangerName = arrangerName;
	}

	public boolean isComposerExists() {
		return composerExists;
	}

	public void setComposerExists(boolean composerExists) {
		this.composerExists = composerExists;
	}

	public boolean isAudioAvailable() {
		return audioAvailable;
	}

	public void setAudioAvailable(boolean audioAvailable) {
		this.audioAvailable = audioAvailable;
	}

	public boolean isMultipleTracks() {
		return multipleTracks;
	}

	public void setMultipleTracks(boolean multipleTracks) {
		this.multipleTracks = multipleTracks;
	}

	public boolean isVideoAvailable() {
		return videoAvailable;
	}

	public void setVideoAvailable(boolean videoAvailable) {
		this.videoAvailable = videoAvailable;
	}
}
