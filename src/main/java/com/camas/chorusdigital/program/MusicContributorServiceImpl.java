package com.camas.chorusdigital.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MusicContributorServiceImpl implements MusicContributorService {
	private static final Logger log = LoggerFactory.getLogger(MusicContributorServiceImpl.class);

	private MusicContributorRepository repository;
	@Autowired
	public void setRepository(MusicContributorRepository repository) {
		this.repository = repository;
	}

	private WorkMusicContributorService workMusicContributorService;
	@Autowired
	public void setWorkMusicContributorService(WorkMusicContributorService workMusicContributorService) {
		this.workMusicContributorService = workMusicContributorService;
	}

	@Override
	public Iterable<MusicContributor> list() {
		return repository.findAllByOrderByLastNameAscFirstNameAsc();
	}

	@Override
	public MusicContributor get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public MusicContributor save(MusicContributor musicContributor) {
		return repository.save(musicContributor);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public MusicContributor findMusicContributor(String firstName, String lastName) {
		return repository.findByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public Iterable<WorkMusicContributor> worksForContributor(MusicContributor musicContributor) {
		return workMusicContributorService.worksForContributor(musicContributor);
	}

	@Override
	public Iterable<MusicContributor> listForFirstNameLikeOrLastNameLike(String searchItem) {
		return repository.findAllByFirstNameLikeOrLastNameLike(searchItem, searchItem);
	}

	@Override
	public Page<MusicContributor> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

}
