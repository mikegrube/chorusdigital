package com.camas.chorusdigital.program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WorkMusicContributorServiceImpl implements WorkMusicContributorService {

	private WorkMusicContributorRepository repository;
	@Autowired
	public void setRepository(WorkMusicContributorRepository repository) {
		this.repository = repository;
	}

	private MusicContributorService musicContributorService;
	@Autowired
	public void setMusicContributorService(MusicContributorService musicContributorService) {
		this.musicContributorService = musicContributorService;
	}

	private WorkService workService;
	@Autowired
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	@Override
	public Iterable<WorkMusicContributor> list() { return repository.findAllByOrderByWorkTitleAsc(); }

	@Override
	public WorkMusicContributor get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public WorkMusicContributor save(WorkMusicContributor workMusicContributor) {
		return repository.save(workMusicContributor);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Iterable<Work> availableWorks() {
		return workService.list();
	}

	@Override
	public Iterable<MusicContributor> availableMusicContributors() {
		return musicContributorService.list();
	}

	@Override
	public Iterable<WorkMusicContributorRole> availableWorkMusicContributorRoles() {

		return Arrays.asList(WorkMusicContributorRole.values());
	}

	@Override
	public Iterable<WorkMusicContributor> workMusicContributorsForWork(Work work) {
		return repository.findByWork(work);
	}

	@Override
	public WorkMusicContributor findWorkMusicContributor(String title, MusicContributor musicContributor, WorkMusicContributorRole role) {

		Iterable<WorkMusicContributor> workMusicContributors = repository.findByMusicContributor(musicContributor);
		for (WorkMusicContributor workMusicContributor : workMusicContributors) {
			if (workMusicContributor.getWork().getTitle().equals(title)) {
				return workMusicContributor;
			}
		}
		return null;
	}

	@Override
	public Iterable<Work> worksForMusicContributor(MusicContributor musicContributor) {
		Iterable<WorkMusicContributor> workMusicContributors = repository.findByMusicContributor(musicContributor);
		List<Work> works = new ArrayList<>();
		for (WorkMusicContributor workMusicContributor : workMusicContributors) {
			works.add(workMusicContributor.getWork());
		}

		return works;
	}

	@Override
	public Iterable<WorkMusicContributor> worksForContributor(MusicContributor musicContributor) {
		return repository.findAllByMusicContributor(musicContributor);
	}

	@Override
	public Work getWork(Long workId) {
		return workService.get(workId);
	}

	@Override
	public WorkMusicContributor composerForWork(Work work) {
		return repository.findByWorkAndWorkMusicContributorRole(work, WorkMusicContributorRole.COMPOSER);
	}

}

