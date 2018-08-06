package com.camas.chorusdigital.program;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkMusicContributorRepository extends CrudRepository<WorkMusicContributor, Long> {

	List<WorkMusicContributor> findAllByOrderByWorkTitleAsc();

	List<WorkMusicContributor> findByWork(Work work);

	WorkMusicContributor findByWorkAndMusicContributorAndWorkMusicContributorRole(Work work, MusicContributor musicContributor, WorkMusicContributorRole role);

	List<WorkMusicContributor> findByMusicContributor(MusicContributor musicContributor);

	Iterable<WorkMusicContributor> findAllByMusicContributor(MusicContributor musicContributor);

	WorkMusicContributor findByWorkAndWorkMusicContributorRole(Work work, WorkMusicContributorRole composer);
}
