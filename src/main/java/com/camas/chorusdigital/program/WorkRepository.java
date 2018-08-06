package com.camas.chorusdigital.program;

import org.springframework.data.repository.PagingAndSortingRepository;

interface WorkRepository extends PagingAndSortingRepository<Work, Long> {

	Iterable<Work> findAllByOrderByTitleAsc();

	Iterable<Work> findAllByTitleContainingIgnoreCase(String titlePart);

}