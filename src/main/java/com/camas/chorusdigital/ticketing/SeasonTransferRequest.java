package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.season.Season;

public class SeasonTransferRequest {

	private Season season;
	private ConcertContributor fromContributor;
	private ConcertContributor toContributor;
	private SeasonSalesClass seasonSalesClass;
	private int count;

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public ConcertContributor getFromContributor() {
		return fromContributor;
	}

	public void setFromContributor(ConcertContributor fromContributor) {
		this.fromContributor = fromContributor;
	}

	public ConcertContributor getToContributor() {
		return toContributor;
	}

	public void setToContributor(ConcertContributor toContributor) {
		this.toContributor = toContributor;
	}

	public SeasonSalesClass getSeasonSalesClass() {
		return seasonSalesClass;
	}

	public void setSeasonSalesClass(SeasonSalesClass seasonSalesClass) {
		this.seasonSalesClass = seasonSalesClass;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
