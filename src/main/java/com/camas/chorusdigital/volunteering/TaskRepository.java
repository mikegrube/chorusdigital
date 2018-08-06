package com.camas.chorusdigital.volunteering;

import org.springframework.data.repository.CrudRepository;

interface TaskRepository extends CrudRepository<Task, Long> {

	Iterable<Task> findAllByOrderByEventDateAsc();

	Iterable<Task> findAllByActiveOrderByEventDateAsc(boolean b);
}
