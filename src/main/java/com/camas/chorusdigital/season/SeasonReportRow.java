package com.camas.chorusdigital.season;

import java.util.Date;

public class SeasonReportRow implements Comparable<SeasonReportRow> {

	private String season;
	private String concert;
	private Date firstConcertDate;
	private Boolean verified;

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getConcert() {
		return concert;
	}

	public void setConcert(String concert) {
		this.concert = concert;
	}

	public Date getFirstConcertDate() {
		return firstConcertDate;
	}

	public void setFirstConcertDate(Date firstConcertDate) {
		this.firstConcertDate = firstConcertDate;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	@Override
	public int compareTo(SeasonReportRow o) {
		if (this.getFirstConcertDate().before(o.getFirstConcertDate())) {
			return -1;
		} else if (this.getFirstConcertDate().after(o.getFirstConcertDate())) {
			return 1;
		} else {
			return 0;
		}

	}
}
