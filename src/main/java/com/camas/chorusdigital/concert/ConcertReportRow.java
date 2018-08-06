package com.camas.chorusdigital.concert;

import java.util.Date;

public class ConcertReportRow implements Comparable<ConcertReportRow> {

	private String title;
	private Date performanceDate;
	private String venue;
	private Boolean verified;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPerformanceDate() {
		return performanceDate;
	}

	public void setPerformanceDate(Date performanceDate) {
		this.performanceDate = performanceDate;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	@Override
	public int compareTo(ConcertReportRow o) {
		if (this.getPerformanceDate().before(o.getPerformanceDate())) {
			return -1;
		} else if (this.getPerformanceDate().after(o.getPerformanceDate())) {
			return 1;
		} else {
			return 0;
		}

	}
}
