package com.camas.chorusdigital.volunteering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Task {
	private static final Logger log = LoggerFactory.getLogger(Task.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static final SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 5)
	private String name;
	private String description;
	private TaskUnit taskUnit;
	@ManyToOne
	private Event event;
	@NotNull
	@Digits(integer = 3, fraction = 0)
	private Integer effort;
	@NotNull
	@Digits(integer = 2, fraction = 0)
	private Integer slots;
	@Transient
	private Integer unfilledSlots;
	private Date eventDate;
	private Date eventTime;
	@Transient
	private String eventDateString;
	@Transient
	private String eventTimeString;
	private String notes;
	private boolean active;

	{
		sdf.setLenient(false);
		sdft.setLenient(false);
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskUnit getTaskUnit() {
		return taskUnit;
	}

	public void setTaskUnit(TaskUnit taskUnit) {
		this.taskUnit = taskUnit;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getUnits() {
		return effort + " " + taskUnit.name();
	}

	public Integer getEffort() {
		return effort;
	}

	public void setEffort(Integer effort) {
		this.effort = effort;
	}

	public Integer getSlots() {
		return slots;
	}

	public void setSlots(Integer slots) {
		this.slots = slots;
	}

	public Integer getUnfilledSlots() {
		return unfilledSlots;
	}

	public void setUnfilledSlots(Integer slots) {
		this.unfilledSlots = slots;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEventDateString() {
		String dts = null;
		try {
			dts = sdf.format(eventDate);
		} catch (Exception e) {
			if (eventDate != null) {
				log.error("Unable to format date: " + eventDate.toString());
			}
		}
		return dts;
	}

	public void setEventDateString(String eventDateString) {
		this.eventDateString = eventDateString;
	}

	public boolean convertEventDate() {

		if (eventDateString == null || eventDateString.equals("")) {
			return false;
		}

		try {
			this.eventDate = sdf.parse(eventDateString);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getEventTimeString() {
		String dts = null;

		if (eventTime == null) {
			return dts;
		}

		try {
			dts = sdft.format(eventTime);
		} catch (Exception e) {
			if (eventTime != null) {
				log.error("Unable to format time: " + eventTime.toString());
			}
		}
		return dts;
	}

	public void setEventTimeString(String eventTimeString) {
		this.eventTimeString = eventTimeString;
	}

	public boolean convertEventTime() {

		if (eventTimeString == null || eventTimeString.equals("")) {
			return true;
		}

		try {
			this.eventTime = sdft.parse(eventTimeString);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String toString() {
		return effort + " " + taskUnit.name() + " for " + event;
	}

}
