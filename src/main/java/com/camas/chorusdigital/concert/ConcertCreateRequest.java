package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.contributor.Contributor;
import com.camas.chorusdigital.program.WorkMusicContributor;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.venue.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConcertCreateRequest {
	private static final Logger log = LoggerFactory.getLogger(ConcertCreateRequest.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	private Season season;
	private String title;
	private String notes;
	private Date[] performanceDateTimes = new Date[3];
	private Venue[] performanceVenues = new Venue[3];
	//We should be able to determine first and last tickets since it's regular (if we know the first year)
	//We should be able to figure out prefix based on year and performance

	List<Contributor> contributors = new ArrayList<>();

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date[] getPerformanceDateTimes() {
		return performanceDateTimes;
	}

	public String[] getPerformanceDateTimeStrings() {

		String[] pdtsa = new String[3];

		for (int i = 0; i < 3; i++) {
			try {
				pdtsa[i] = sdf.format(performanceDateTimes[i]);
			} catch (Exception e) {
				if (performanceDateTimes[i] != null) {
					log.info("Unable to format datetime: " + performanceDateTimes[i]);
				}
				pdtsa[i] = null;
			}
		}

		return pdtsa;
	}

	public void setPerformanceDateTimeStrings(String[] pdtss) {

		for (int i = 0; i < 3; i++) {

			try {
				performanceDateTimes[i] = sdf.parse(pdtss[i]);
			} catch (ParseException e) {
				if (pdtss[i] != null) {
					log.info("Unable to parse datetime: " + pdtss[i]);
				}
				performanceDateTimes[i] = null;
			}
		}
	}

	public void setPerformanceDateTimes(Date[] performanceDateTimes) {
		this.performanceDateTimes = performanceDateTimes;
	}

	public Venue[] getPerformanceVenues() {
		return performanceVenues;
	}

	public void setPerformanceVenues(Venue[] performanceVenues) {
		this.performanceVenues = performanceVenues;
	}

	public List<Contributor> getContributors() {
		return contributors;
	}

	public void setContributors(List<Contributor> contributors) {
		this.contributors = contributors;
	}
}
