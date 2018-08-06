package com.camas.chorusdigital.program;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MusicContributorService {

	Iterable<MusicContributor> list();

	MusicContributor get(Long id);

	MusicContributor save(MusicContributor musicContributor);

	void delete(Long id);

	MusicContributor findMusicContributor(String firstName, String lastName);

	Iterable<WorkMusicContributor> worksForContributor(MusicContributor musicContributor);

	Iterable<MusicContributor> listForFirstNameLikeOrLastNameLike(String searchItem);

	Page<MusicContributor> findAll(PageRequest name);
}
