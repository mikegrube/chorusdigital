package com.camas.chorusdigital;

import com.camas.chorusdigital.security.Role;
import com.camas.chorusdigital.security.RoleService;
import com.camas.chorusdigital.security.User;
import com.camas.chorusdigital.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private RoleService roleService;
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	List<String> requiredRoles = Arrays.asList("ROLE_ADMIN"
												, "ROLE_USER"
												, "ROLE_READER"
												, "ROLE_TICKETER"
												, "ROLE_SUPERVISOR"
												, "ROLE_VOLUNTEER");

	List<String> requiredUsers = Arrays.asList("admin");

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		log.info("Ensuring users and roles");

		Iterable<Role> roles = roleService.list();
		for (String requiredRole : requiredRoles) {
			boolean found = false;
			for (Role role : roles) {
				if (requiredRole.equals(role.getRole())) {
					found = true;
				}
			}
			if (!found) {
				Role newRole = new Role();
				newRole.setRole(requiredRole);
				roleService.save(newRole);
			}
		}

		Iterable<User> users = userService.list();
		for (String requiredUser : requiredUsers) {
			boolean exists = false;
			for (User user : users) {
				if (requiredUser.equals(user.getUserName())) {
					exists = true;
				}
			}
			if (!exists) {
				User newUser = new User();
				newUser.setUserName(requiredUser);
				newUser.setPassword("password");
				userService.save(newUser);
			}
		}
	}

}