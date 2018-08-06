package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.performance.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class PerformanceContributorSalesClass {
	private static final Logger log = LoggerFactory.getLogger(Concert.class);

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@NotNull(message="ConcertContributor cannot be null")
	private ConcertContributor concertContributor;

	@ManyToOne
	@NotNull(message="Performance cannot be null")
	private Performance performance;

	@ManyToOne
	@NotNull(message="ConcertSalesClass cannot be null")
	private ConcertSalesClass salesClass;

	private int count;
	private TicketStatus ticketStatus;

	public PerformanceContributorSalesClass() {

	}

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

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}

	public ConcertSalesClass getSalesClass() {
		return salesClass;
	}

	public void setSalesClass(ConcertSalesClass concertSalesClass) {
		this.salesClass = concertSalesClass;
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
