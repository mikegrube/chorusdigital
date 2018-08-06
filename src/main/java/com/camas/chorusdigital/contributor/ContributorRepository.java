package com.camas.chorusdigital.contributor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface ContributorRepository extends PagingAndSortingRepository<Contributor, Long> {

	Iterable<Contributor> findAllByOrderByLastNameAscFirstNameAsc();

	Iterable<Contributor> findAllByMember(boolean member);

	Page<Contributor> findAllByMember(boolean member, Pageable pageRequest);

	Contributor findByFirstNameAndLastName(String firstName, String lastName);

	Contributor findByLastName(String lastName);	//Only for Bank and Unknown

}