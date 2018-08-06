package com.camas.chorusdigital.security;

import com.camas.chorusdigital.contributor.Contributor;

public interface UserService {

	Iterable<User> list();

	User get(Long id);

	User save(User user);

	void delete(Long id);

	User findByUserName(String userName);

	Iterable<Role> availableRoles();

	User registerUser(RegistrationRequest registrationRequest);

	String checkForNameOrEmail(RegistrationRequest registrationRequest);

	User findCurrentUser();

	boolean isCurrentUser(User user);

	Iterable<Contributor> availableMembers();
}
