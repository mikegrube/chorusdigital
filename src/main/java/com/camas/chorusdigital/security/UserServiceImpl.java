package com.camas.chorusdigital.security;

import com.camas.chorusdigital.contributor.Contributor;
import com.camas.chorusdigital.contributor.ContributorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@Profile("springdatajpa")
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository repository;
	@Autowired
	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	private RoleService roleService;
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private PasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private ContributorService contributorService;
	@Autowired
	public void setContributorService(ContributorService contributorService) {
		this.contributorService = contributorService;
	}

	@Override
	public Iterable<User> list() {
		return repository.findAll();
	}

	@Override
	public User get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public User save(User user) {

		log.info("Saving user: " + user.getUserName());

		if(user.getPassword() != null && user.getPassword() != ""){
			user.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
		}

		return repository.save(user);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public User findByUserName(String userName) {
		return repository.findByUserName(userName);
	}

	@Override
	public Iterable<Role> availableRoles() {
		return roleService.list();
	}

	@Override
	public String checkForNameOrEmail(RegistrationRequest registrationRequest) {

		User prevUser = repository.findByUserName(registrationRequest.getUserName());
		if (prevUser != null) {
			return "userName";
		}
		prevUser = repository.findByEmail(registrationRequest.getEmail());
		if (prevUser != null) {
			return "email";
		}

		//All ok
		return null;
	}

	@Override
	public User findCurrentUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return repository.findByUserName(userName);
	}

	@Override
	public boolean isCurrentUser(User user) {
		return user == findCurrentUser();
	}

	@Override
	public Iterable<Contributor> availableMembers() {

		List<Contributor> members = new ArrayList<>();

		Iterable<Contributor> contributors = contributorService.list();
		for (Contributor contributor : contributors) {
			if (contributor.isMember()) {
				members.add(contributor);
			}
		}

		return members;
	}

	@Override
	public User registerUser(RegistrationRequest registrationRequest) {

		User user = new User();
		user.setUserName(registrationRequest.getUserName());
		user.setFirstName(registrationRequest.getFirstName());
		user.setLastName(registrationRequest.getLastName());
		user.setPassword(registrationRequest.getPassword());
		user.setEnabled(true);
		user.setEmail(registrationRequest.getEmail());
		user.setPhone(registrationRequest.getPhone());
		user.setTermsAgreed(registrationRequest.isTermsAgreed());

		user = save(user);

		Role vRole = roleService.findByName("ROLE_USER");
		user.addRole(vRole);
		vRole = roleService.findByName("ROLE_VOLUNTEER");
		user.addRole(vRole);

		user = save(user);

		return user;
	}

}
