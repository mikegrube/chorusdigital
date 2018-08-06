package com.camas.chorusdigital.admin;

import com.camas.chorusdigital.concert.*;
import com.camas.chorusdigital.contributor.Contributor;
import com.camas.chorusdigital.concert.ConcertContributorRole;
import com.camas.chorusdigital.contributor.ContributorService;
import com.camas.chorusdigital.contributor.Section;
import com.camas.chorusdigital.performance.Performance;
import com.camas.chorusdigital.performance.PerformanceService;
import com.camas.chorusdigital.program.*;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.season.SeasonService;
import com.camas.chorusdigital.venue.Venue;
import com.camas.chorusdigital.venue.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoaderServiceImpl implements LoaderService {
	private static final Logger log = LoggerFactory.getLogger(LoaderServiceImpl.class);

	private int pos = 0;

	private SeasonService seasonService;
	@Autowired
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	private ConcertService concertService;
	@Autowired
	public void setService(ConcertService concertService) {
		this.concertService = concertService;
	}

	private PerformanceService performanceService;
	@Autowired
	public void setPerformanceService(PerformanceService performanceService) {
		this.performanceService = performanceService;
	}

	private VenueService venueService;
	@Autowired
	public void setVenueService(VenueService venueService) {
		this.venueService = venueService;
	}

	private WorkService workService;
	@Autowired
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	private ConcertWorkTrackService concertWorkTrackService;
	@Autowired
	public void setConcertWorkTrackService(ConcertWorkTrackService concertWorkTrackService) {
		this.concertWorkTrackService = concertWorkTrackService;
	}

	private ConcertWorkService concertWorkService;
	@Autowired
	public void setConcertWorkService(ConcertWorkService concertWorkService) {
		this.concertWorkService = concertWorkService;
	}

	private ContributorService contributorService;
	@Autowired
	public void setContributorService(ContributorService contributorService) {
		this.contributorService = contributorService;
	}

	private ConcertContributorService concertContributorService;
	@Autowired
	public void setConcertContributorService(ConcertContributorService concertContributorService) {
		this.concertContributorService = concertContributorService;
	}

	private MusicContributorService musicContributorService;
	@Autowired
	public void setMusicContributorService(MusicContributorService musicContributorService) {
		this.musicContributorService = musicContributorService;
	}

	private WorkMusicContributorService workMusicContributorService;
	@Autowired
	public void setWorkMusicContributorService(WorkMusicContributorService workMusicContributorService) {
		this.workMusicContributorService = workMusicContributorService;
	}

	@Override
	public void postYaml(String type, String yaml) {

		ObjectMapper yamlObjectMapper = new ObjectMapper(new YAMLFactory());

		//Load a concert
		if (type.equals("Concert")) {
			ConcertStruct cs;
			try {
				cs = yamlObjectMapper.readValue(yaml, ConcertStruct.class);
/*
				log.info("Concert is : " + cs.title);
				log.info("Performance on : " + cs.performances.get(0).datetime);
				log.info("A movement is: " + cs.works.get(2).movements.get(0).title);
				log.info("A member is: " + cs.contributors.get(0).firstName);
*/
				Concert concert = createOrUpdateConcert(cs);
				for (PerformanceStruct ps : cs.performances) {
					createOrUpdatePerformance(concert, ps);
				}

				pos = 0;		// Make sure we start at zero for a concert
				for (WorkStruct ws : cs.works) {
					createOrUpdateWork(concert, ws);
				}
				for (ContributorStruct cos : cs.contributors) {
					createOrUpdateContributor(concert, cos);
				}

			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}
		}
	}

	private void createOrUpdateContributor(Concert concert, ContributorStruct cos) {
		Contributor contributor = contributorService.findContributor(cos.firstName, cos.lastName);
		if (contributor == null) {
			contributor = new Contributor();
			contributor.setFirstName(cos.firstName);
			contributor.setLastName(cos.lastName);
		}
		if (valued(cos.email)) {
			contributor.setEmail(cos.email);
		}
		if (valued(cos.homePhone)) {
			contributor.setEmail(cos.homePhone);
		}
		if (valued(cos.mobilePhone)) {
			contributor.setEmail(cos.mobilePhone);
		}
		if (valued(cos.section)) {
			contributor.setSection(Section.valueOf(cos.section));
		}
		contributor = contributorService.save(contributor);

		ConcertContributor concertContributor = concertContributorService.findConcertContributor(concert, contributor, ConcertContributorRole.valueOf(cos.role));
		if (concertContributor == null) {
			concertContributor = new ConcertContributor();
			concertContributor.setConcert(concert);
			concertContributor.setContributor(contributor);
			concertContributor.setConcertContributorRole(ConcertContributorRole.valueOf(cos.role));
			concertContributor = concertContributorService.save(concertContributor);
		}
	}

	private boolean valued(String val) {
		return val != null && !val.equals("");
	}

	private void createOrUpdateWork(Concert concert, WorkStruct ws) {

		MusicContributor composer = null;
		for (MusicContributorStruct mcs : ws.musicContributors) {
			if (WorkMusicContributorRole.valueOf(mcs.role) == WorkMusicContributorRole.COMPOSER) {
				composer = musicContributorService.findMusicContributor(mcs.firstName, mcs.lastName);
				if (composer == null) {
					composer = new MusicContributor();
					composer.setFirstName(mcs.firstName);
					composer.setLastName(mcs.lastName);
					if (valued(mcs.born)) {
						composer.setBorn(Integer.parseInt(mcs.born));
					}
					if (valued(mcs.died)) {
						composer.setDied(Integer.parseInt(mcs.died));
					}
					composer = musicContributorService.save(composer);
				}
			}
		}

		Work work = workService.findWork(ws.title, composer);
		if (work == null) {
			work = new Work();
			work.setTitle(ws.title);
			work = workService.save(work);
			WorkMusicContributor workMusicContributor = new WorkMusicContributor();
			workMusicContributor.setWork(work);
			workMusicContributor.setMusicContributor(composer);
			workMusicContributor.setWorkMusicContributorRole(WorkMusicContributorRole.COMPOSER);
			workMusicContributorService.save(workMusicContributor);
		}
		if (valued(ws.notes)) {
			work.setNotes(ws.notes);
		}
		if (valued(ws.tags)) {
			work.setNotes(ws.tags);
		}
		work = workService.save(work);

		//Now get the rest of the contributors
		for (MusicContributorStruct mcs : ws.musicContributors) {
			if (WorkMusicContributorRole.valueOf(mcs.role) != WorkMusicContributorRole.COMPOSER) {
				MusicContributor contributor = musicContributorService.findMusicContributor(mcs.firstName, mcs.lastName);
				if (contributor == null) {
					contributor = new MusicContributor();
					contributor.setFirstName(mcs.firstName);
					contributor.setLastName(mcs.lastName);
					if (valued(mcs.born)) {
						contributor.setBorn(Integer.parseInt(mcs.born));
					}
					if (valued(mcs.died)) {
						contributor.setDied(Integer.parseInt(mcs.died));
					}
					contributor = musicContributorService.save(contributor);
				}
				WorkMusicContributor workMusicContributor = new WorkMusicContributor();
				workMusicContributor.setWork(work);
				workMusicContributor.setMusicContributor(contributor);
				workMusicContributor.setWorkMusicContributorRole(WorkMusicContributorRole.valueOf(mcs.role));
				workMusicContributorService.save(workMusicContributor);
			}
		}

		ConcertWork concertWork = concertWorkService.findConcertWork(concert, work);
		if (concertWork == null) {
			concertWork = new ConcertWork();
			concertWork.setConcert(concert);
			concertWork.setWork(work);
			if (valued(ws.performedByGroup)) {
				concertWork.setPerformedByGroup(ws.performedByGroup.equalsIgnoreCase("true") ? true : false);
			}
			concertWork.setTrack(++pos);
			concertWork = concertWorkService.save(concertWork);
		}
/*
		int mpos = 0;
		if (ws.movements != null){
			for (MovementStruct ms : ws.movements) {
				ConcertWorkTrack concertWorkTrack = concertWorkTrackService.findMovement(work, ms.title);
				if (concertWorkTrack == null) {
					concertWorkTrack = new ConcertWorkTrack();
					concertWorkTrack.setConcertWork(work);
					concertWorkTrack.setTitle(ms.title);
					concertWorkTrack.setTrack(++mpos);
				}
				if (valued(ms.notes)) {
					concertWorkTrack.setNotes(ms.notes);
				}
				concertWorkTrack = concertWorkTrackService.save(concertWorkTrack);
			}
		}
*/
	}

	private void createOrUpdatePerformance(Concert concert, PerformanceStruct ps) {
		Performance performance = performanceService.findPerformance(concert, ps.dateTime);
		if (performance == null) {
			performance = new Performance();
			performance.setConcert(concert);
			performance.setDateTimeString(ps.dateTime);
		}
		VenueStruct vs = ps.venue;
		Venue venue = venueService.findVenue(vs.name);
		if (venue == null) {
			venue = new Venue();
			venue.setName(vs.name);
		}
		if (valued(vs.street)) {
			venue.setStreet(vs.street);
		}
		if (valued(vs.city)) {
			venue.setCity(vs.city);
		}
		if (valued(vs.capacity)) {
			venue.setCapacity(vs.capacity);
		}
		venue = venueService.save(venue);

		performance.setVenue(venue);

		if (valued(ps.firstTicket)) {
			performance.setFirstTicket(Integer.parseInt(ps.firstTicket));
		}
		if (valued(ps.lastTicket)) {
			performance.setLastTicket(Integer.parseInt(ps.lastTicket));
		}
		if (valued(ps.notes)) {
			performance.setNotes(ps.notes);
		}
		performance = performanceService.save(performance);

	}

	private Concert createOrUpdateConcert(ConcertStruct cs) {
		Season season = createOrUpdateSeason(cs.seasonYear);
		Concert concert = concertService.findConcert(season, cs.title);
		if (concert == null) {
			concert = new Concert();
			concert.setSeason(season);
			concert.setTitle(cs.title);
		}
		if (valued(cs.prefix)) {
			concert.setPrefix(cs.prefix);
		}
		if (valued(cs.notes)) {
			concert.setNotes(cs.notes);
		}
		concert = concertService.save(concert);

		return concert;
	}

	private Season createOrUpdateSeason(String seasonYear) {
		Season season = seasonService.findSeason(seasonYear);
		if (season == null) {
			season = new Season();
			season.setStartYear(seasonYear);
			season = seasonService.save(season);
		}

		return season;
	}

}
