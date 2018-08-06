package com.camas.chorusdigital.program;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface WorkService {

	Iterable<Work> list();

	Work get(Long id);

	Work save(Work work);

	void delete(Long id);

	Iterable<ConcertWorkTrack> concertWorkTracksForConcertWork(ConcertWork concertWork);

	Iterable<WorkMusicContributor> workMusicContributorsForWork(Work work);

	Work findWork(String title, MusicContributor composer);

	MusicContributor composerForWork(Work work);

	Iterable<ConcertWork> concertsForWork(Work work);

	Iterable<Work> listForTitleLike(String titlePart);

	Page<Work> findAll(PageRequest pageRequest);

	MusicContributor findUnknownComposer();
}