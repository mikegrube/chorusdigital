package com.camas.chorusdigital.reporting;

public class PerformanceInfo {

	private String dateString;
	private String venueName;

	private boolean dateKnown = true;
	private boolean venueKnown = true;
	private boolean dateAndSeasonMatch = true;

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public boolean isDateKnown() {
		return dateKnown;
	}

	public void setDateKnown(boolean dateKnown) {
		this.dateKnown = dateKnown;
	}

	public boolean isVenueKnown() {
		return venueKnown;
	}

	public void setVenueKnown(boolean venueKnown) {
		this.venueKnown = venueKnown;
	}

	public boolean isDateAndSeasonMatch() {
		return dateAndSeasonMatch;
	}

	public void setDateAndSeasonMatch(boolean dateAndSeasonMatch) {
		this.dateAndSeasonMatch = dateAndSeasonMatch;
	}

}
