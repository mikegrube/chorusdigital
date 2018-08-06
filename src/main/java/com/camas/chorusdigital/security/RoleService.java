package com.camas.chorusdigital.security;

public interface RoleService {

	Iterable<Role> list();

	Role get(Long id);

	Role save(Role role);

	void delete(Long id);

	Role findByName(String name);
}
