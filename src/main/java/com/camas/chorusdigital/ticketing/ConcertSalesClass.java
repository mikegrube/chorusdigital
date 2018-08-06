package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.Concert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ConcertSalesClass {
	private static final Logger log = LoggerFactory.getLogger(Concert.class);

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@NotNull(message="Concert cannot be null")
	private Concert concert;

	private String name;
	private double value;

	private static String unknownClass = "UNASSIGNED";

	public ConcertSalesClass() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public static String getUnknownClass() {
		return unknownClass;
	}

	public static void setUnknownClass(String unknownClass) {
		ConcertSalesClass.unknownClass = unknownClass;
	}
}
