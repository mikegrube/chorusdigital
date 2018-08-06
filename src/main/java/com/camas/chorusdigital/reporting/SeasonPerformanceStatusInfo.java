package com.camas.chorusdigital.reporting;

import java.util.Date;

public class SeasonPerformanceStatusInfo {

	private String season;
	private String concertTitle;
	private String concertPrefix;
	private Date performanceDate;
	private String venueName;

	private boolean concertVerified;				//Marked verified?
	private boolean concertVideoExists;				//Has a video playlist?
	private boolean concertPrefixOk;				//If there is a prefix, is it in order with dating?
	private boolean performanceDateVerified;		//Does performance date match season, is it in range of seasons?
	private boolean venueVerified;					//Do we know where it really was?

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getConcertTitle() {
		return concertTitle;
	}

	public void setConcertTitle(String concertTitle) {
		this.concertTitle = concertTitle;
	}

	public String getConcertPrefix() {
		return concertPrefix;
	}

	public void setConcertPrefix(String concertPrefix) {
		this.concertPrefix = concertPrefix;
	}

	public boolean isConcertVideoExists() {
		return concertVideoExists;
	}

	public void setConcertVideoExists(boolean concertVideoExists) {
		this.concertVideoExists = concertVideoExists;
	}

	public Date getPerformanceDate() {
		return performanceDate;
	}

	public void setPerformanceDate(Date performanceDate) {
		this.performanceDate = performanceDate;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public boolean isConcertVerified() {
		return concertVerified;
	}

	public void setConcertVerified(boolean concertVerified) {
		this.concertVerified = concertVerified;
	}

	public boolean isPerformanceDateVerified() {
		return performanceDateVerified;
	}

	public void setPerformanceDateVerified(boolean performanceDateVerified) {
		this.performanceDateVerified = performanceDateVerified;
	}

	public boolean isVenueVerified() {
		return venueVerified;
	}

	public void setVenueVerified(boolean venueVerified) {
		this.venueVerified = venueVerified;
	}

	public boolean isConcertPrefixOk() {
		return concertPrefixOk;
	}

	public void setConcertPrefixOk(boolean concertPrefixOk) {
		this.concertPrefixOk = concertPrefixOk;
	}
}
