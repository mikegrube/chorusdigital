package com.camas.chorusdigital.program;

public interface WorkMusicContributorService {
	
	Iterable<WorkMusicContributor> list();

	WorkMusicContributor get(Long id);

	WorkMusicContributor save(WorkMusicContributor workMusicContributor);

	void delete(Long id);

	Iterable<Work> availableWorks();

	Iterable<MusicContributor> availableMusicContributors();

	Iterable<WorkMusicContributorRole> availableWorkMusicContributorRoles();

	Iterable<WorkMusicContributor> workMusicContributorsForWork(Work work);

	WorkMusicContributor findWorkMusicContributor(String title, MusicContributor musicContributor, WorkMusicContributorRole role);

	Iterable<Work> worksForMusicContributor(MusicContributor musicContributor);

	Iterable<WorkMusicContributor> worksForContributor(MusicContributor musicContributor);

	Work getWork(Long workId);

	WorkMusicContributor composerForWork(Work work);
}
