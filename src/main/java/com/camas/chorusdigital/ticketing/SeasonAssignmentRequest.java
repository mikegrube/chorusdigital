package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.season.Season;

public class SeasonAssignmentRequest {

	private Season season;
	private int ticketsPerClass;

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public int getTicketsPerClass() {
		return ticketsPerClass;
	}

	public void setTicketsPerClass(int ticketsPerClass) {
		this.ticketsPerClass = ticketsPerClass;
	}
}
