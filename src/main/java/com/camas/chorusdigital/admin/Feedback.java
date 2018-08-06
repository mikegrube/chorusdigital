package com.camas.chorusdigital.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Feedback {
	private static final Logger log = LoggerFactory.getLogger(Feedback.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	@Id
	@GeneratedValue
	private Long id;

	private String user;
	private Date dateTime;
	private String text;
	private String followup;

	public Feedback() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFollowup() {
		return followup;
	}

	public void setFollowup(String followup) {
		this.followup = followup;
	}
}
