package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.season.Season;

public interface SeasonContributorSalesClassService {

	Iterable<SeasonContributorSalesClass> list();

	SeasonContributorSalesClass get(Long id);

	SeasonContributorSalesClass save(SeasonContributorSalesClass seasonContributorSalesClass);

	void delete(Long id);

	Season getActiveSeason();

	Iterable<ConcertContributor> getActiveConcertContributors();

	Iterable<SeasonSalesClass> getActiveSeasonSalesClasses();

	Iterable<TicketStatus> getTicketStatuses();

	void assignTickets(SeasonAssignmentRequest seasonAssignmentRequest);

	void transferTickets(SeasonTransferRequest seasonTransferRequest);

	void sellTickets(SeasonSellRequest seasonSellRequest);
}
