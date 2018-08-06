package com.camas.chorusdigital.security;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUserName(String userName);

	User findByEmail(String email);
}
