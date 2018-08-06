package com.camas.chorusdigital.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	private RoleRepository repository;
	@Autowired
	public void setRepository(RoleRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Role> list() {
		return repository.findAll();
	}

	@Override
	public Role get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Role save(Role role) {

		log.info("Saving role: " + role.getRole());

		return repository.save(role);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Role findByName(String name) {
		return repository.findByRole(name);
	}

}