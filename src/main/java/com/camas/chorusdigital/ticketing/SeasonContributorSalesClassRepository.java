package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.season.Season;
import org.springframework.data.repository.CrudRepository;

interface SeasonContributorSalesClassRepository extends CrudRepository<SeasonContributorSalesClass, Long> {

	SeasonContributorSalesClass findByConcertContributorAndSalesClassAndTicketStatus(ConcertContributor concertContributor, SeasonSalesClass salesClass, TicketStatus ticketStatus);
}

