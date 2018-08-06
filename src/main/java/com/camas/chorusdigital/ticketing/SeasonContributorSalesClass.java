package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.performance.Performance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class SeasonContributorSalesClass {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@NotNull(message="ConcertContributor cannot be null")
	private ConcertContributor concertContributor;

	@ManyToOne
	@NotNull(message="SeasonSalesClass cannot be null")
	private SeasonSalesClass salesClass;

	private int count;
	private TicketStatus ticketStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ConcertContributor getConcertContributor() {
		return concertContributor;
	}

	public void setConcertContributor(ConcertContributor concertContributor) {
		this.concertContributor = concertContributor;
	}

	public SeasonSalesClass getSalesClass() {
		return salesClass;
	}

	public void setSalesClass(SeasonSalesClass salesClass) {
		this.salesClass = salesClass;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
}
