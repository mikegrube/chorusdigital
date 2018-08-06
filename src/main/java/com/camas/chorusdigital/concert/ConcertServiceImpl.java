package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.contributor.Contributor;
import com.camas.chorusdigital.contributor.ContributorService;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.performance.PerformanceService;
import com.camas.chorusdigital.program.ConcertWork;
import com.camas.chorusdigital.program.ConcertWorkService;
import com.camas.chorusdigital.program.ConcertWorkTrack;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.season.SeasonService;
import com.camas.chorusdigital.venue.Venue;
import com.camas.chorusdigital.venue.VenueService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConcertServiceImpl implements ConcertService {
	private static final Logger log = LoggerFactory.getLogger(ConcertServiceImpl.class);

	private ConcertRepository repository;
	@Autowired
	public void setRepository(ConcertRepository repository) {
		this.repository = repository;
	}

	private PerformanceService performanceService;
	@Autowired
	public void setPerformanceService(PerformanceService performanceService) {
		this.performanceService = performanceService;
	}

	private ConcertWorkService concertWorkService;
	@Autowired
	public void setConcertWorkService(ConcertWorkService concertWorkService) {
		this.concertWorkService = concertWorkService;
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

	private ContributorService contributorService;
	@Autowired
	public void setContributorService(ContributorService contributorService) {
		this.contributorService = contributorService;
	}

	private VenueService venueService;
	@Autowired
	public void setVenueService(VenueService venueService) {
		this.venueService = venueService;
	}

	@Override
	public Iterable<Concert> list() {
		return list(true);
	}

	@Override
	public Page<Concert> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

	@Override
	public Collection<ConcertReportRow> getConcertRecords() {

		List<ConcertReportRow> records = new ArrayList<>();

		Iterable <Concert> concerts = repository.findAllByOrderBySeasonStartYearAscTitleAsc();
		for (Concert concert : concerts) {

			Iterable<Performance> performances = performancesForConcert(concert);
			for (Performance performance : performances) {
				ConcertReportRow concertReportRow = new ConcertReportRow();
				concertReportRow.setTitle(concert.getTitle());
				concertReportRow.setPerformanceDate(performance.getDateTime());
				concertReportRow.setVenue(performance.getVenue().getName());

				if (concert.getNotes() == null) {
					concertReportRow.setVerified(false);
				} else {
					concertReportRow.setVerified(concert.getNotes().contains("ver"));
				}

				records.add(concertReportRow);
			}
		}

		Collections.sort(records);

		return records;
	}

	@Override
	public Iterable<Concert> list(boolean current) {

		if (current) {
//			return repository.findBySeason(seasonService.getActiveSeason());
//		} else {
			return repository.findAllByOrderByTitleAsc();
		}
		//Bogus return
		return null;
	}

	@Override
	public Iterable<Concert> listBySeason() {
		return repository.findAllByOrderBySeasonStartYearAscTitleAsc();
	}

	@Override
	public Concert get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Concert save(Concert concert) {
		return repository.save(concert);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Concert getActiveConcert() {
		return repository.findByActive(true);
	}

	@Override
	public Iterable<Concert> listForSeason(Season season) {

		return repository.findBySeason(season);
	}

	@Override
	public Iterable<Performance> performancesForConcert(Concert concert) {
		return performanceService.listForConcert(concert);
	}

	@Override
	public Iterable<ConcertWork> concertWorksForConcert(Concert concert) {
		return concertWorkService.concertWorksForConcert(concert);
	}

	@Override
	public	Iterable<ConcertContributor> concertContributorsForConcert(Concert concert) {
		return concertContributorService.concertContributorsForConcert(concert);
	}

	@Override
	public Iterable<Season> availableSeasons() {
		return seasonService.list();
	}

	@Override
	public Concert findConcert(Season season, String title) {
		return repository.findBySeasonAndTitle(season, title);
	}

	@Override
	public Iterable<Concert> listForTitleLike(String titlePart) {

		return repository.findAllByTitleContainingIgnoreCase(titlePart);
	}

	@Override
	public Iterable<Venue> availableVenues() {
		return venueService.list();
	}

	@Override
	public Iterable<Contributor> availableContributors() {
		return contributorService.list();
	}

	@Override
	public Concert fullCreate(ConcertCreateRequest concertCreateRequest) {

		boolean preBPT = Integer.parseInt(concertCreateRequest.getSeason().getStartYear()) < 2000;

		Concert concert = new Concert();
		concert.setSeason(concertCreateRequest.getSeason());
		if (preBPT) {
			//Prefix is unused
			concert.setPrefix("0");
		}
		concert.setTitle(concertCreateRequest.getTitle());
		concert = repository.save(concert);

		for (int i = 0; i < 3; i++) {
			if (concertCreateRequest.getPerformanceDateTimes()[i] != null && concertCreateRequest.getPerformanceVenues()[i] != null) {
				Performance performance = new Performance();
				performance.setConcert(concert);
				performance.setDateTime(concertCreateRequest.getPerformanceDateTimes()[i]);
				performance.setVenue(concertCreateRequest.getPerformanceVenues()[i]);
				if (preBPT) {
					performance.setFirstTicket(0);
					performance.setLastTicket(0);
				}
				performance = performanceService.save(performance);
			}
		}

		for (Contributor contributor : concertCreateRequest.getContributors()) {
			ConcertContributor concertContributor = new ConcertContributor();
			concertContributor.setConcert(concert);
			concertContributor.setContributor(contributor);
			concertContributor = concertContributorService.save(concertContributor);
		}

		return concert;
	}

	@Override
	public boolean audioExistsForConcertWork(ConcertWork concertWork) {
		return concertWorkService.audioExistsForConcertWork(concertWork);
	}

	@Override
	public void makeActive(Concert concert) {

		//Unset the active flag from any other concert
		for (Concert aConcert : repository.findAll()) {
			if ((aConcert.getTitle() != concert.getTitle()) && aConcert.isActive()) {
				aConcert.setActive(false);
				repository.save(aConcert);
			}
		}

		concert.setActive(true);
		repository.save(concert);

	}

}
