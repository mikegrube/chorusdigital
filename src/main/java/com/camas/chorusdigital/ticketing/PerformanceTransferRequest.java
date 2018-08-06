package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.performance.Performance;

public class PerformanceTransferRequest {

	private Performance performance;
	private ConcertContributor fromContributor;
	private ConcertContributor toContributor;
	private int count;

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
