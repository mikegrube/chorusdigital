package com.camas.chorusdigital.program;

import java.util.ArrayList;
import java.util.List;

import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.concert.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConcertWorkServiceImpl implements ConcertWorkService {

	private ConcertWorkRepository repository;
	@Autowired
	public void setRepository(ConcertWorkRepository repository) {
		this.repository = repository;
	}

	private WorkService workService;
	@Autowired
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	private ConcertService concertService;
	@Autowired
	public void setConcertService(ConcertService concertService) {
		this.concertService = concertService;
	}

	private ConcertWorkTrackService concertWorkTrackService;
	@Autowired
	public void setConcertWorkTrackService(ConcertWorkTrackService concertWorkTrackService) {
		this.concertWorkTrackService = concertWorkTrackService;
	}

	private WorkMusicContributorService workMusicContributorService;
	@Autowired
	public void setWorkMusicContributorService(WorkMusicContributorService workMusicContributorService) {
		this.workMusicContributorService = workMusicContributorService;
	}

	@Override
	public Iterable<ConcertWork> list() { return repository.findAll(); }

	@Override
	public ConcertWork get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public ConcertWork save(ConcertWork concertWork) {
		return repository.save(concertWork);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<WorkDisplay> availableWorks() {

		List<WorkDisplay> workDisplays = new ArrayList<>();
		Iterable<Work> works = workService.list();
		for (Work work : works) {
			WorkDisplay workDisplay = new WorkDisplay();
			workDisplay.setId(work.getId());
			WorkMusicContributor workMusicContributor = workMusicContributorService.composerForWork(work);
			if (workMusicContributor != null) {
				workDisplay.setTitle(work.getTitle() + " - " + workMusicContributor.getMusicContributor().getName());
			} else {
				workDisplay.setTitle(work.getTitle() + " - missing");
			}
			workDisplays.add(workDisplay);
		}
		return workDisplays;
	}

	@Override
	public Iterable<Concert> availableConcerts() {
		return concertService.list();
	}

	@Override
	public Iterable<ConcertWork> concertWorksForConcert(Concert concert) {
		return repository.findByConcertOrderByTrackAsc(concert);
	}

	@Override
	public ConcertWork findConcertWork(Concert concert, Work work) {
		return repository.findByConcertAndWork(concert, work);
	}

	@Override
	public Iterable<ConcertWork> concertsForWork(Work work) {

		return repository.findByWork(work);
	}

	@Override
	public Iterable<ConcertWorkTrack> tracksForConcertWork(ConcertWork concertWork) {
		return concertWorkTrackService.concertWorkTracksForConcertWork(concertWork);
	}

	@Override
	public boolean audioExistsForConcertWork(ConcertWork concertWork) {
		Iterable<ConcertWorkTrack> concertWorkTracks = concertWorkTrackService.concertWorkTracksForConcertWork(concertWork);
		return concertWorkTracks.iterator().hasNext();
	}

	@Override
	public Concert findConcert(Long concertId) {
		return concertService.get(concertId);
	}

}

