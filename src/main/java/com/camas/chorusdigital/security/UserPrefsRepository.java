package com.camas.chorusdigital.security;


import org.springframework.data.repository.CrudRepository;

interface UserPrefsRepository extends CrudRepository<UserPrefs, Long> {

	UserPrefs findByUser(User user);
}
