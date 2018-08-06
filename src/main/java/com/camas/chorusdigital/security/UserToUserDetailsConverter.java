package com.camas.chorusdigital.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {

	@Override
	public UserDetails convert(User user) {

		UserDetailsImpl userDetails = new UserDetailsImpl();

		if (user != null) {
			userDetails.setUserName(user.getUserName());
			userDetails.setPassword(user.getEncryptedPassword());
			userDetails.setEnabled(user.isEnabled());
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			user.getRoles().forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role.getRole()));
			});
			userDetails.setAuthorities(authorities);
		}
		return userDetails;
	}
}
