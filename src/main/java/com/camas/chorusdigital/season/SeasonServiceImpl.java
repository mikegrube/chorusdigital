package com.camas.chorusdigital.season;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertContributor;
import com.camas.chorusdigital.concert.ConcertContributorRole;
import com.camas.chorusdigital.concert.ConcertService;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.program.*;
import com.camas.chorusdigital.reporting.ConcertInfo;
import com.camas.chorusdigital.reporting.PerformanceInfo;
import com.camas.chorusdigital.reporting.SeasonInfo;
import com.camas.chorusdigital.reporting.WorkInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SeasonServiceImpl implements SeasonService {

	private static final Logger log = LoggerFactory.getLogger(SeasonServiceImpl.class);

	private SeasonRepository repository;
	@Autowired
	public void setRepository(SeasonRepository repository) {
		this.repository = repository;
	}

	private ConcertService concertService;
	@Autowired
	public void setConcertService(ConcertService concertService) {
		this.concertService = concertService;
	}

	private WorkService workService;
	@Autowired
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	@Override
	public Iterable<Season> list() {
		return repository.findAllByOrderByStartYear();
	}

	@Override
	public Season get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Season save(Season season) {
		return repository.save(season);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<Concert> concertsForSeason(Season season) {
		return concertService.listForSeason(season);
	}

	@Override
	public Season findSeason(String startYear) {
		return repository.findByStartYear(startYear);
	}

	@Override
	public Season getActiveSeason() {
		return concertService.getActiveConcert().getSeason();
	}

	@Override
	public Page<Season> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

	@Override
	public Season getNextSeason() {

		int thisSeasonYear = Integer.parseInt(getActiveSeason().getStartYear());
		Integer nextSeasonYear = thisSeasonYear + 1;
		String nextSeasonYearString = nextSeasonYear.toString();
		Season nextSeason = repository.findByStartYear(nextSeasonYearString);
		if (nextSeason == null) {
			nextSeason = new Season();
			nextSeason.setStartYear(nextSeasonYearString);
			nextSeason = repository.save(nextSeason);
		}

		return nextSeason;
	}

	@Override
	public List<SeasonReportRow> getSeasonRecords() {

		List<SeasonReportRow> records = new ArrayList<>();

		Iterable <Season> seasons = repository.findAllByOrderByStartYear();
		for (Season season : seasons) {

			Iterable<Concert> concerts = concertsForSeason(season);
			for (Concert concert : concerts) {
				SeasonReportRow seasonReportRow = new SeasonReportRow();
				seasonReportRow.setSeason(season.toString());
				seasonReportRow.setConcert(concert.getTitle());

				Date firstDate = null;
				Iterable<Performance> performances = concertService.performancesForConcert(concert);
				for (Performance performance : performances) {
					if (firstDate == null || performance.getDateTime().before(firstDate)) {
						firstDate = performance.getDateTime();
					}
				}
				if (firstDate == null) {
					try {
						firstDate = new SimpleDateFormat("yyyymmdd").parse("19000101");
					} catch (Exception e) {
						log.error("Exception in creating date");
					}
				}
				seasonReportRow.setFirstConcertDate(firstDate);

				if (concert.getNotes() == null) {
					seasonReportRow.setVerified(false);
				} else {
					seasonReportRow.setVerified(concert.getNotes().contains("ver"));
				}

				records.add(seasonReportRow);
			}
		}

		Collections.sort(records);

		return records;
	}

	@Override
	public List<SeasonInfo> getSeasonInfoRecords() {

		List<SeasonInfo> seasonInfoList = new ArrayList<>();

		Iterable<Season> seasons = list();
		for (Season season : seasons) {

			boolean allConcertsClean = true;

			SeasonInfo seasonInfo = new SeasonInfo();
			seasonInfo.setSeason(season.toString());

			Iterable<Concert> concerts = concertsForSeason(season);
			int concertCt = 0;
			int concertCl = 0;
			for (Concert concert : concerts) {
				concertCt++;
				ConcertInfo concertInfo = new ConcertInfo();
				concertInfo.setTitle(concert.getTitle());
				concertInfo.setPrefix(concert.getPrefix());
				concertInfo.setVideoExists(concert.videoExists());

				addPerformances(concert, concertInfo);

				addWorks(concert, concertInfo);

				addMemberInfo(concert, concertInfo);

				if (concertInfo.isClean()) {
					concertCl++;
				}

				if (seasonInfo.getConcertInfoList().size() > 0) {
					ConcertInfo previousConcertInfo = seasonInfo.getConcertInfoList().get(seasonInfo.getConcertInfoList().size() - 1);
					concertInfo.setConsecutivePrefix(checkPrefix(concertInfo, previousConcertInfo));
				} else {
					concertInfo.setConsecutivePrefix(false);
				}

				seasonInfo.addConcertInfo(concertInfo);
			}
			seasonInfo.setConcertCt(seasonInfo.getConcertInfoList().size());
			seasonInfo.setConcertCl(concertCl);

			seasonInfoList.add(seasonInfo);

		}

		log.info("Found " + seasonInfoList.size() + " season.");
		return seasonInfoList;
	}

	private boolean checkPrefix(ConcertInfo concertInfo, ConcertInfo previousConcertInfo) {
		String previousPrefix = previousConcertInfo.getPrefix();
		String prefix = concertInfo.getPrefix();

		return Integer.parseInt(prefix) == Integer.parseInt(previousPrefix) + 1;
	}

	private void addMemberInfo(Concert concert, ConcertInfo concertInfo) {

		Iterable<ConcertContributor> contributors = concertService.concertContributorsForConcert(concert);
		for (ConcertContributor concertContributor : contributors) {
			if (concertContributor.getConcertContributorRole() == ConcertContributorRole.MEMBER) {
				concertInfo.setMemberListExists(true);
			}
		}
	}

	private void addWorks(Concert concert, ConcertInfo concertInfo) {

		boolean nonGroup = false;

		Iterable<ConcertWork> works = concertService.concertWorksForConcert(concert);
		for (ConcertWork concertWork : works) {
			WorkInfo workInfo = new WorkInfo();
			workInfo.setName(concertWork.getWork().getTitle());

			MusicContributor composer = workService.composerForWork(concertWork.getWork());
			if (composer == null) {
				workInfo.setComposerExists(false);
			} else {
				workInfo.setComposerName(composer.getName());
			}

			Iterable<WorkMusicContributor> arrangers = workService.workMusicContributorsForWork(concertWork.getWork());
			for (WorkMusicContributor arranger : arrangers) {
				if (arranger.getWorkMusicContributorRole() == WorkMusicContributorRole.ARRANGER) {
					if (workInfo.getArrangerName() == null) {
						workInfo.setArrangerName(arranger.getMusicContributor().getName());
					} else {
						workInfo.setArrangerName(workInfo.getArrangerName() + "; " + arranger.getMusicContributor().getName());
					}
				}

				if (!concertWork.isPerformedByGroup()) {
					nonGroup = true;
				}
			}
			workInfo.setAudioAvailable(concertWork.audioExists());
			workInfo.setVideoAvailable(concertWork.videoExists());
		}
		concertInfo.setWorksExist(concertInfo.getWorkInfoList().size() > 0);

		concertInfo.setNonGroupWorksExist(nonGroup);

	}

	private void addPerformances(Concert concert, ConcertInfo concertInfo) {
		Iterable<Performance> performances = concertService.performancesForConcert(concert);
		for (Performance performance : performances) {
			PerformanceInfo performanceInfo = new PerformanceInfo();
			performanceInfo.setDateString(performance.getDateTimeString());
			performanceInfo.setVenueName(performance.getVenue().getName());

			checkDate(performanceInfo, performance, concert.getSeason().getStartYear());

			performanceInfo.setVenueKnown(performanceInfo.getVenueName() != "Other Area");

			concertInfo.addPerformanceInfo(performanceInfo);
		}
		concertInfo.setPerformanceCt(concertInfo.getPerformanceInfoList().size());
	}

	private void checkDate(PerformanceInfo performanceInfo, Performance performance, String startYear) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(performance.getDateTime());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		int seasonYear = Integer.parseInt(startYear);

		if (month > 7) {
			performanceInfo.setDateAndSeasonMatch(year == seasonYear);
		} else {
			performanceInfo.setDateAndSeasonMatch(year == seasonYear + 1);
		}

		performanceInfo.setDateKnown(year > 1983);
	}

}
