package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.performance.Performance;

public class PerformanceSellRequest {

	private Performance performance;
	private ConcertContributor concertContributor;
	private ConcertSalesClass concertSalesClass;
	private int count;

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}

	public ConcertContributor getConcertContributor() {
		return concertContributor;
	}

	public void setConcertContributor(ConcertContributor concertContributor) {
		this.concertContributor = concertContributor;
	}

	public ConcertSalesClass getConcertSalesClass() {
		return concertSalesClass;
	}

	public void setConcertSalesClass(ConcertSalesClass concertSalesClass) {
		this.concertSalesClass = concertSalesClass;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
