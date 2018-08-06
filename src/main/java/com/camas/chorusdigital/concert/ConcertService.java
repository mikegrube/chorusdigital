package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.contributor.Contributor;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.program.ConcertWork;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.venue.Venue;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface ConcertService {

	Iterable<Concert> list();

	Iterable<Concert> list(boolean current);

	Iterable<Concert> listBySeason();

	Concert get(Long id);

	Concert save(Concert concert);

	void delete(Long id);

	Concert getActiveConcert();

	Iterable<Concert> listForSeason(Season season);

	Iterable<Performance> performancesForConcert(Concert concert);

	Iterable<ConcertWork> concertWorksForConcert(Concert concert);

	Iterable<ConcertContributor> concertContributorsForConcert(Concert concert);

	Iterable<Season> availableSeasons();

	Concert findConcert(Season season, String title);

	Iterable<Concert> listForTitleLike(String titlePart);

	Iterable<Venue> availableVenues();

	Iterable<Contributor> availableContributors();

	Concert fullCreate(ConcertCreateRequest concertCreateRequest);

	boolean audioExistsForConcertWork(ConcertWork concertWork);

	void makeActive(Concert concert);

	Page<Concert> findAll(PageRequest title);

	Collection<ConcertReportRow> getConcertRecords();

}