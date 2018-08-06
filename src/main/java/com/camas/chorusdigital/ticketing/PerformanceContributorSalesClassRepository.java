package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.performance.Performance;
import org.springframework.data.repository.CrudRepository;

interface PerformanceContributorSalesClassRepository extends CrudRepository<PerformanceContributorSalesClass, Long> {

	PerformanceContributorSalesClass findByPerformanceAndConcertContributorAndSalesClassAndTicketStatus(Performance performance, ConcertContributor concertContributor, ConcertSalesClass concertSalesClass, TicketStatus ticketStatus);
}

