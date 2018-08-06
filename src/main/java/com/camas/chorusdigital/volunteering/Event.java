package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.season.Season;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue
	private Long id;

	private String name;		//For Fundraiser or Other
	private EventType type;
	@ManyToOne
	private Season season = null;
	@ManyToOne
	private Concert concert = null;
	@ManyToOne
	private Performance performance = null;

	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String toString() {

		String res;

		switch (type) {
			case SEASON:
				res = "SEASON: " + season.toString();
				break;
			case CONCERT:
				res = "CONCERT: " + concert.getTitle();
				break;
			case PERFORMANCE:
				res = "PERFORMANCE: " + performance.toString();
				break;
			case FUNDRAISER:
				res = "FUNDRAISER: " + name;
				break;
			case CONTINUING:
				res = "CONTINUING: " + name;
				break;
			case OTHER:
				res = "OTHER: " + name;
				break;
			default:
				res = "UNKNOWN: " + name;
				break;
		}
		return res;
	}
}
