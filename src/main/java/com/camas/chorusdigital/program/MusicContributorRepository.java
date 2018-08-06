package com.camas.chorusdigital.program;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface MusicContributorRepository extends PagingAndSortingRepository<MusicContributor, Long> {

	Iterable<MusicContributor> findAllByOrderByLastNameAscFirstNameAsc();

	Iterable<MusicContributor> findAllByFirstNameLikeOrLastNameLike(String first, String last);

	MusicContributor findByFirstNameAndLastName(String firstName, String lastName);

}