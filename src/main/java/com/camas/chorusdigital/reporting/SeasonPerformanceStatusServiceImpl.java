package com.camas.chorusdigital.reporting;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.concert.ConcertContributorRole;
import com.camas.chorusdigital.concert.ConcertService;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.program.ConcertWork;
import com.camas.chorusdigital.program.MusicContributor;
import com.camas.chorusdigital.program.WorkMusicContributor;
import com.camas.chorusdigital.program.WorkMusicContributorRole;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.season.SeasonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class SeasonPerformanceStatusServiceImpl implements SeasonPerformanceStatusService {
	private static final Logger log = LoggerFactory.getLogger(SeasonPerformanceStatusServiceImpl.class);

	private SeasonService seasonService;
	@Autowired
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	private ConcertService concertService;
	@Autowired
	public void setConcertService(ConcertService concertService) {
		this.concertService = concertService;
	}

	private String currentSeason = null;
	private String currentConcertTitle = null;
	private String currentConcertPrefix = null;
	private boolean currentConcertVerified = false;
	private boolean currentConcertVideoExists = false;
	private String lastConcertPrefix = "000";

	@Override
	public List<SeasonPerformanceStatusInfo> getSeasonPerformanceStatusInfos() {

		List<SeasonPerformanceStatusInfo> seasonInfoList = new ArrayList<>();

		Iterable<Season> seasons = seasonService.list();
		for (Season season : seasons) {

			currentSeason = season.toString();

			Iterable<Concert> concerts = seasonService.concertsForSeason(season);
			for (Concert concert : concerts) {

				currentConcertTitle = concert.getTitle();
				currentConcertPrefix = concert.getPrefix();
				currentConcertVideoExists = concert.videoExists();
				currentConcertVerified = !(concert.getNotes() == null) && concert.getNotes().contains("ver");

				Iterable<Performance> performances = concertService.performancesForConcert(concert);
				for (Performance performance : performances) {

					SeasonPerformanceStatusInfo seasonPerformanceStatusInfo = new SeasonPerformanceStatusInfo();
					seasonPerformanceStatusInfo.setSeason(currentSeason);
					seasonPerformanceStatusInfo.setConcertTitle(currentConcertTitle);
					seasonPerformanceStatusInfo.setConcertPrefix(currentConcertPrefix);
					seasonPerformanceStatusInfo.setConcertVerified(currentConcertVerified);
					seasonPerformanceStatusInfo.setConcertVideoExists(currentConcertVideoExists);
					seasonPerformanceStatusInfo.setPerformanceDate(performance.getDateTime());
					seasonPerformanceStatusInfo.setVenueName(performance.getVenue().getName());

					performVerifications(seasonPerformanceStatusInfo);

					seasonInfoList.add(seasonPerformanceStatusInfo);
				}
			}
		}

		return seasonInfoList;
	}

	private void performVerifications(SeasonPerformanceStatusInfo info) {

		info.setPerformanceDateVerified(dateMatchesSeason(info));
		info.setVenueVerified(venueKnown(info));
		info.setConcertPrefixOk(prefixExistsInOrder(info));
	}

	private boolean prefixExistsInOrder(SeasonPerformanceStatusInfo info) {

		boolean ok = false;

		if (info.getConcertPrefix().equals("000")) {
			if (lastConcertPrefix.equals("000")) {
				ok = true;
			} else {
				ok = false;
			}
		} else {	//It's a good prefix
			if (lastConcertPrefix.equals("000")) {
				ok = true;
			} else {	//Need to be consecutive
				ok = Integer.parseInt(info.getConcertPrefix()) == Integer.parseInt(lastConcertPrefix) + 1;
			}
		}

		//Ready for next
		lastConcertPrefix = info.getConcertPrefix();

		return ok;
	}

	private boolean venueKnown(SeasonPerformanceStatusInfo info) {
		return !info.getVenueName().equals("Other Area");
	}

	private boolean dateMatchesSeason(SeasonPerformanceStatusInfo info) {

		boolean ok = true;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(info.getPerformanceDate());
		int year = calendar.get(Calendar.YEAR);

		if (year < 1984) {
			ok = false;
		} else {

			int month = calendar.get(Calendar.MONTH);

			String seasonStartYear = info.getSeason().substring(7, 11);
			int seasonYear = Integer.parseInt(seasonStartYear);

			log.info("Comparing " + year + " to " + seasonYear);

			if (month > 7) {
				ok = year == seasonYear;
			} else {
				ok = year == seasonYear + 1;
			}
		}

		return ok;
	}

}
