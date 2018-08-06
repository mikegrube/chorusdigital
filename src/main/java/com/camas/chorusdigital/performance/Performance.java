package com.camas.chorusdigital.performance;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.venue.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Performance {
	private static final Logger log = LoggerFactory.getLogger(Performance.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@NotNull(message="Concert cannot be null")
	private	Concert concert;

	@ManyToOne
	@NotNull(message="Venue cannot be null")
	private	Venue venue;

	@NotNull(message="Datetime cannot be null")
	private Date dateTime;

	@Min(1)
	@Max(999)
	private Integer firstTicket;
	@Min(1)
	@Max(999)
	private Integer lastTicket;
	@Size(max=255,message="Notes must be 255 characters or fewer")
	private String notes;

	public Performance() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(String idString) {
		this.id = Long.parseLong(idString);
	}

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public String getDateTimeString() {
		String dts = null;
		try {
			dts = sdf.format(dateTime);
		} catch (Exception e) {
			if (dateTime != null) {
				log.error("Unable to format date: " + dateTime.toString());
			}
		}
		return dts;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void setDateTimeString(String dateTimeString) {
		try {
			this.dateTime = sdf.parse(dateTimeString);
		} catch (ParseException e) {
			log.error("Unable to parse date: " + dateTimeString);
		}
	}

	public Integer getFirstTicket() {
		return firstTicket;
	}

	public void setFirstTicket(Integer firstTicket) {
		this.firstTicket = firstTicket;
	}

	public Integer getLastTicket() {
		return lastTicket;
	}

	public void setLastTicket(Integer lastTicket) {
		this.lastTicket = lastTicket;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return concert.getTitle() + " - " + dateTime;
	}
}