package com.camas.chorusdigital.program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class WorkServiceImpl implements WorkService {

	private WorkRepository repository;
	@Autowired
	public void setRepository(WorkRepository repository) {
		this.repository = repository;
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

	private ConcertWorkService concertWorkService;
	@Autowired
	public void setConcertWorkService(ConcertWorkService concertWorkService) {
		this.concertWorkService = concertWorkService;
	}

	private MusicContributorService musicContributorService;
	@Autowired
	public void setMusicContributorService(MusicContributorService musicContributorService) {
		this.musicContributorService = musicContributorService;
	}

	@Override
	public Iterable<Work> list() { return repository.findAllByOrderByTitleAsc(); }

	@Override
	public Page<Work> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

	@Override
	public MusicContributor findUnknownComposer() {
		return musicContributorService.findMusicContributor("unknown","composer");
	}

	@Override
	public Work get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Work save(Work work) {
		return repository.save(work);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<ConcertWorkTrack> concertWorkTracksForConcertWork(ConcertWork concertWork) {
		return concertWorkTrackService.concertWorkTracksForConcertWork(concertWork);
	}

	@Override
	public Iterable<WorkMusicContributor> workMusicContributorsForWork(Work work) {
		return workMusicContributorService.workMusicContributorsForWork(work);
	}

	@Override
	public	Work findWork(String title, MusicContributor composer) {

		WorkMusicContributor workMusicContributor = workMusicContributorService.findWorkMusicContributor(title, composer, WorkMusicContributorRole.COMPOSER);
		if (workMusicContributor != null) {
			return workMusicContributor.getWork();
		}
		return null;
	}

	@Override
	public MusicContributor composerForWork(Work work) {

		Iterable<WorkMusicContributor> workMusicContributors = workMusicContributorService.workMusicContributorsForWork(work);
		for (WorkMusicContributor workMusicContributor : workMusicContributors) {
			if (workMusicContributor.getWorkMusicContributorRole() == WorkMusicContributorRole.COMPOSER) {
				return workMusicContributor.getMusicContributor();
			}
		}
		return null;
	}

	@Override
	public Iterable<ConcertWork> concertsForWork(Work work) {
		return concertWorkService.concertsForWork(work);
	}

	@Override
	public Iterable<Work> listForTitleLike(String titlePart) {

		return repository.findAllByTitleContainingIgnoreCase(titlePart);
	}

}

