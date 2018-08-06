package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.concert.*;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.performance.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PerformanceContributorSalesClassServiceImpl implements PerformanceContributorSalesClassService {
	private static final Logger log = LoggerFactory.getLogger(PerformanceContributorSalesClassServiceImpl.class);

	private PerformanceContributorSalesClassRepository repository;
	@Autowired
	public void setRepository(PerformanceContributorSalesClassRepository repository) {
		this.repository = repository;
	}

	private ConcertService concertService;
	@Autowired
	public void setConcertService(ConcertService concertService) {
		this.concertService = concertService;
	}

	private PerformanceService performanceService;
	@Autowired
	public void setPerformanceService(PerformanceService performanceService) {
		this.performanceService = performanceService;
	}

	private ConcertSalesClassService concertSalesClassService;
	@Autowired
	public void setConcertSalesClassService(ConcertSalesClassService concertSalesClassService) {
		this.concertSalesClassService = concertSalesClassService;
	}

	private ConcertContributorService concertContributorService;
	@Autowired
	public void setConcertContributorService(ConcertContributorService concertContributorService) {
		this.concertContributorService = concertContributorService;
	}

	@Override
	public Iterable<PerformanceContributorSalesClass> list() {

		//TODO: Return only for active concert
		return repository.findAll();
	}

	@Override
	public PerformanceContributorSalesClass get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public PerformanceContributorSalesClass save(PerformanceContributorSalesClass performanceContributorSalesClass) {
		return repository.save(performanceContributorSalesClass);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<Performance> getActivePerformances() {
		Concert concert = concertService.getActiveConcert();
		return performanceService.listForConcert(concert);
	}

	@Override
	public Iterable<ConcertSalesClass> getActiveConcertSalesClasses() {
		return concertSalesClassService.getActiveConcertSalesClasses();
	}

	@Override
	public Iterable<ConcertContributor> getActiveConcertContributors() {
		return concertContributorService.getActiveConcertContributors();
	}

	@Override
	public Iterable<TicketStatus> getTicketStatuses() {
		return Arrays.asList(TicketStatus.values());
	}

	@Override
	public Concert getActiveConcert() {
		return concertService.getActiveConcert();
	}

	@Override
	public void assignTickets(PerformanceAssignmentRequest performanceAssignmentRequest) {

		Iterable<ConcertContributor> contributors = getActiveConcertContributors();
		Iterable<Performance> performances = getActivePerformances();

		for (ConcertContributor concertContributor : contributors) {
			if (concertContributor.getConcertContributorRole() == ConcertContributorRole.MEMBER) {
				for (Performance performance : performances) {
					PerformanceContributorSalesClass performanceContributorSalesClass = findOrCreate(performance, concertContributor, concertSalesClassService.findUnassigned(performance.getConcert()), TicketStatus.ASSIGNED);

					updateTicketCount(performanceContributorSalesClass, performanceAssignmentRequest.getTicketsPerPerformance());
				}
			}
		}
	}

	private PerformanceContributorSalesClass findOrCreate(Performance performance, ConcertContributor concertContributor, ConcertSalesClass concertSalesClass, TicketStatus ticketStatus) {

		PerformanceContributorSalesClass performanceContributorSalesClass =
				repository.findByPerformanceAndConcertContributorAndSalesClassAndTicketStatus(performance, concertContributor, concertSalesClass, ticketStatus);
		if (performanceContributorSalesClass == null) {
			//We haven't assigned any before
			performanceContributorSalesClass = new PerformanceContributorSalesClass();
			performanceContributorSalesClass.setPerformance(performance);
			performanceContributorSalesClass.setConcertContributor(concertContributor);
			performanceContributorSalesClass.setSalesClass(concertSalesClass);
			performanceContributorSalesClass.setTicketStatus(ticketStatus);
			performanceContributorSalesClass.setCount(0);
		}

		return performanceContributorSalesClass;
	}

	@Override
	public void transferTickets(PerformanceTransferRequest performanceTransferRequest) {

		PerformanceContributorSalesClass fromPerformanceContributorSalesClass = findOrCreate(performanceTransferRequest.getPerformance(), performanceTransferRequest.getFromContributor(), concertSalesClassService.findUnassigned(performanceTransferRequest.getPerformance().getConcert()), TicketStatus.ASSIGNED);
		PerformanceContributorSalesClass toPerformanceContributorSalesClass = findOrCreate(performanceTransferRequest.getPerformance(), performanceTransferRequest.getToContributor(), concertSalesClassService.findUnassigned(performanceTransferRequest.getPerformance().getConcert()), TicketStatus.ASSIGNED);

		if (fromPerformanceContributorSalesClass.getCount() >= performanceTransferRequest.getCount()) {
			updateTicketCount(fromPerformanceContributorSalesClass, - performanceTransferRequest.getCount());
			updateTicketCount(toPerformanceContributorSalesClass, performanceTransferRequest.getCount());
		} else {
			log.error("Insufficient tickets to transfer from " + fromPerformanceContributorSalesClass.getConcertContributor().getContributor().getName() +  " to " + toPerformanceContributorSalesClass.getConcertContributor().getContributor().getName());
		}
	}

	@Override
	public void sellTickets(PerformanceSellRequest performanceSellRequest) {

		PerformanceContributorSalesClass performanceContributorSalesClass = findOrCreate(performanceSellRequest.getPerformance(), performanceSellRequest.getConcertContributor(), concertSalesClassService.findUnassigned(performanceSellRequest.getPerformance().getConcert()), TicketStatus.ASSIGNED);
		PerformanceContributorSalesClass soldContributorSalesClass = findOrCreate(performanceSellRequest.getPerformance(), performanceSellRequest.getConcertContributor(), performanceSellRequest.getConcertSalesClass(), TicketStatus.SOLD);

		if (performanceContributorSalesClass.getCount() >= performanceSellRequest.getCount()) {
			updateTicketCount(performanceContributorSalesClass, - performanceSellRequest.getCount());
			updateTicketCount(soldContributorSalesClass, performanceSellRequest.getCount());
		} else {
			log.error("Insufficient tickets to sell from " + performanceContributorSalesClass.getConcertContributor().getContributor().getName());
		}
	}

	private void updateTicketCount(PerformanceContributorSalesClass performanceContributorSalesClass, int count) {
		performanceContributorSalesClass.setCount(performanceContributorSalesClass.getCount() + count);
		repository.save(performanceContributorSalesClass);
	}

}
