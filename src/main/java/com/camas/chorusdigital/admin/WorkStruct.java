package com.camas.chorusdigital.admin;

import java.util.List;

public class WorkStruct {

	String title;

	String performedByGroup;
	String notes;
	String tags;

	List<MusicContributorStruct> musicContributors;
	List<MovementStruct> movements;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPerformedByGroup() {
		return performedByGroup;
	}

	public void setPerformedByGroup(String performedByGroup) {
		this.performedByGroup = performedByGroup;
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

	public List<MovementStruct> getMovements() {
		return movements;
	}

	public void setMovements(List<MovementStruct> movements) {
		this.movements = movements;
	}

	public List<MusicContributorStruct> getMusicContributors() {
		return musicContributors;
	}

	public void setMusicContributors(List<MusicContributorStruct> musicContributors) {
		this.musicContributors = musicContributors;
	}
}
