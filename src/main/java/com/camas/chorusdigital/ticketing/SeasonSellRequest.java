package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.season.Season;

public class SeasonSellRequest {

	private Season Season;
	private ConcertContributor concertContributor;
	private SeasonSalesClass seasonSalesClass;
	private int count;

	public Season getSeason() {
		return Season;
	}

	public void setSeason(Season season) {
		Season = season;
	}

	public ConcertContributor getConcertContributor() {
		return concertContributor;
	}

	public void setConcertContributor(ConcertContributor concertContributor) {
		this.concertContributor = concertContributor;
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
