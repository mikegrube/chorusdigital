package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.performance.Performance;

public interface PerformanceContributorSalesClassService {

	Iterable<PerformanceContributorSalesClass> list();

	PerformanceContributorSalesClass get(Long id);

	PerformanceContributorSalesClass save(PerformanceContributorSalesClass performanceContributorSalesClass);

	void delete(Long id);

	Iterable<Performance> getActivePerformances();

	Iterable<ConcertSalesClass> getActiveConcertSalesClasses();

	Iterable<ConcertContributor> getActiveConcertContributors();

	Iterable<TicketStatus> getTicketStatuses();

	Concert getActiveConcert();

	void assignTickets(PerformanceAssignmentRequest performanceAssignmentRequest);

	void transferTickets(PerformanceTransferRequest performanceTransferRequest);

	void sellTickets(PerformanceSellRequest performanceSellRequest);
}
