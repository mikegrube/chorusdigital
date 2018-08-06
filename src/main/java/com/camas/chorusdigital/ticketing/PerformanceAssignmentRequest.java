package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.performance.Performance;

public class PerformanceAssignmentRequest {

	private Concert concert;
	private int ticketsPerPerformance;

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}

	public int getTicketsPerPerformance() {
		return ticketsPerPerformance;
	}

	public void setTicketsPerPerformance(int ticketsPerPerformance) {
		this.ticketsPerPerformance = ticketsPerPerformance;
	}
}
