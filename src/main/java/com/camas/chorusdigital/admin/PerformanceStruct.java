package com.camas.chorusdigital.admin;

public class PerformanceStruct {

	String dateTime;
	String firstTicket;
	String lastTicket;
	String notes;

	VenueStruct venue;

	public PerformanceStruct() {}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getFirstTicket() {
		return firstTicket;
	}

	public void setFirstTicket(String firstTicket) {
		this.firstTicket = firstTicket;
	}

	public String getLastTicket() {
		return lastTicket;
	}

	public void setLastTicket(String lastTicket) {
		this.lastTicket = lastTicket;
	}

	public VenueStruct getVenue() {
		return venue;
	}

	public void setVenue(VenueStruct venue) {
		this.venue = venue;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
