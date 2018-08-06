package com.camas.chorusdigital.admin;

import java.util.List;

/* Used for yaml parsing */
public class ConcertStruct {

	String seasonYear;
	String title;
	String prefix;
	String notes;

	List<PerformanceStruct> performances;
	List<WorkStruct> works;
	List<ContributorStruct> contributors;

	public ConcertStruct() {}

	public String getSeasonYear() {
		return seasonYear;
	}

	public void setSeasonYear(String seasonYear) {
		this.seasonYear = seasonYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<PerformanceStruct> getPerformances() {
		return performances;
	}

	public void setPerformances(List<PerformanceStruct> performances) {
		this.performances = performances;
	}

	public List<WorkStruct> getWorks() {
		return works;
	}

	public void setWorks(List<WorkStruct> works) {
		this.works = works;
	}

	public List<ContributorStruct> getContributors() {
		return contributors;
	}

	public void setContributors(List<ContributorStruct> contributors) {
		this.contributors = contributors;
	}
}
