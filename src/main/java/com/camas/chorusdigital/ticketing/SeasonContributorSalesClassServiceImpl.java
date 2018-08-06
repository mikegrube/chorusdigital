package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.concert.ConcertContributorRole;
import com.camas.chorusdigital.concert.ConcertContributorService;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.season.SeasonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SeasonContributorSalesClassServiceImpl implements SeasonContributorSalesClassService {
	private static final Logger log = LoggerFactory.getLogger(SeasonContributorSalesClassServiceImpl.class);

	private SeasonContributorSalesClassRepository repository;

	@Autowired
	public void setRepository(SeasonContributorSalesClassRepository repository) {
		this.repository = repository;
	}

	private SeasonService seasonService;
	@Autowired
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	private ConcertContributorService concertContributorService;
	@Autowired
	public void setConcertContributorService(ConcertContributorService concertContributorService) {
		this.concertContributorService = concertContributorService;
	}

	private SeasonSalesClassService seasonSalesClassService;
	@Autowired
	public void setSeasonSalesClassService(SeasonSalesClassService seasonSalesClassService) {
		this.seasonSalesClassService = seasonSalesClassService;
	}

	@Override
	public Iterable<SeasonContributorSalesClass> list() {

		//TODO: Only for the current season
		return repository.findAll();
	}

	@Override
	public SeasonContributorSalesClass get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public SeasonContributorSalesClass save(SeasonContributorSalesClass seasonContributorSalesClass) {
		return repository.save(seasonContributorSalesClass);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Season getActiveSeason() {
		return seasonService.getActiveSeason();
	}

	@Override
	public Iterable<ConcertContributor> getActiveConcertContributors() {
		return concertContributorService.getActiveConcertContributors();
	}

	@Override
	public Iterable<SeasonSalesClass> getActiveSeasonSalesClasses() {
		return seasonSalesClassService.getActiveSeasonSalesClasses();
	}

	@Override
	public Iterable<TicketStatus> getTicketStatuses() {
		return Arrays.asList(TicketStatus.values());
	}

	@Override
	public void assignTickets(SeasonAssignmentRequest seasonAssignmentRequest) {

		Iterable<ConcertContributor> contributors = getActiveConcertContributors();
		Iterable<SeasonSalesClass> salesClasses = getActiveSeasonSalesClasses();

		for (ConcertContributor concertContributor : contributors) {
			if (concertContributor.getConcertContributorRole() == ConcertContributorRole.MEMBER) {
				for (SeasonSalesClass seasonSalesClass : salesClasses) {
					SeasonContributorSalesClass seasonContributorSalesClass = findOrCreate(concertContributor, seasonSalesClass, TicketStatus.ASSIGNED);

					updateTicketCount(seasonContributorSalesClass, seasonAssignmentRequest.getTicketsPerClass());
				}
			}
		}
	}

	private SeasonContributorSalesClass findOrCreate(ConcertContributor concertContributor, SeasonSalesClass seasonSalesClass, TicketStatus ticketStatus) {

		SeasonContributorSalesClass seasonContributorSalesClass =
				repository.findByConcertContributorAndSalesClassAndTicketStatus(concertContributor, seasonSalesClass, ticketStatus);
		if (seasonContributorSalesClass == null) {
			//We haven't assigned any before
			seasonContributorSalesClass = new SeasonContributorSalesClass();
			seasonContributorSalesClass.setConcertContributor(concertContributor);
			seasonContributorSalesClass.setSalesClass(seasonSalesClass);
			seasonContributorSalesClass.setTicketStatus(ticketStatus);
			seasonContributorSalesClass.setCount(0);
		}

		return seasonContributorSalesClass;
	}

	@Override
	public void transferTickets(SeasonTransferRequest seasonTransferRequest) {

		SeasonContributorSalesClass fromSeasonContributorSalesClass = findOrCreate(seasonTransferRequest.getFromContributor(), seasonTransferRequest.getSeasonSalesClass(), TicketStatus.ASSIGNED);
		SeasonContributorSalesClass toSeasonContributorSalesClass = findOrCreate(seasonTransferRequest.getToContributor(), seasonTransferRequest.getSeasonSalesClass(), TicketStatus.ASSIGNED);

		if (fromSeasonContributorSalesClass.getCount() >= seasonTransferRequest.getCount()) {
			updateTicketCount(fromSeasonContributorSalesClass, - seasonTransferRequest.getCount());
			updateTicketCount(toSeasonContributorSalesClass, seasonTransferRequest.getCount());
		} else {
			log.error("Insufficient tickets to transfer from " + fromSeasonContributorSalesClass.getConcertContributor().getContributor().getName() +  " to " + toSeasonContributorSalesClass.getConcertContributor().getContributor().getName());
		}
	}

	@Override
	public void sellTickets(SeasonSellRequest seasonSellRequest) {

		SeasonContributorSalesClass seasonContributorSalesClass = findOrCreate(seasonSellRequest.getConcertContributor(), seasonSellRequest.getSeasonSalesClass(), TicketStatus.ASSIGNED);
		SeasonContributorSalesClass soldContributorSalesClass = findOrCreate(seasonSellRequest.getConcertContributor(), seasonSellRequest.getSeasonSalesClass(), TicketStatus.SOLD);

		if (seasonContributorSalesClass.getCount() >= seasonSellRequest.getCount()) {
			updateTicketCount(seasonContributorSalesClass, - seasonSellRequest.getCount());
			updateTicketCount(soldContributorSalesClass, seasonSellRequest.getCount());
		} else {
			log.error("Insufficient tickets to sell from " + seasonContributorSalesClass.getConcertContributor().getContributor().getName());
		}
	}

	private void updateTicketCount(SeasonContributorSalesClass seasonContributorSalesClass, int count) {
		seasonContributorSalesClass.setCount(seasonContributorSalesClass.getCount() + count);
		repository.save(seasonContributorSalesClass);
	}

}
