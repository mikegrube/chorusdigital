package com.camas.chorusdigital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service("userDetailsService")
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

//	@Qualifier(value = "userToUserDetails")
	private UserToUserDetailsConverter userUserDetailsConverter;
	@Autowired
	public void setUserUserDetailsConverter(UserToUserDetailsConverter userUserDetailsConverter) {
		this.userUserDetailsConverter = userUserDetailsConverter;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userUserDetailsConverter.convert(userService.findByUserName(username));
	}
}
