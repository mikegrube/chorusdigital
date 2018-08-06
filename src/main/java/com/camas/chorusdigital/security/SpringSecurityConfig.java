package com.camas.chorusdigital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.formLogin()
					.loginPage("/login").permitAll()
					.and()
				.logout()
					.permitAll()
					.and()
				.authorizeRequests()
					.antMatchers("/",
							"/admin/user/register",
							"/help/list", "/help/*",
							"/about",
							"/photo/*")
						.permitAll()
					.antMatchers("/season/new", "season/save",
							"/concert/new", "/concert/save",
							"/performance/new", "/performance/save",
							"/venue/new", "/venue/save",
							"/contributor/new", "/contributor/save",
							"/work/new", "/work/save",
							"/concertWork/new", "/concertWork/save",
							"/musicContributor/new", "/musicContributor/save",
							"/photo/new", "/photo/save")
						.hasRole("ADMIN")
					.antMatchers("/season/list", "/season/*", "/season/brochure/*",
							"/concert/list", "/concert/*", "/concert/program/*",
							"/performance/list", "/performance/*",
							"/venue/list", "/venue/*",
							"/concertWork/*",
							"/work/list", "/work/*",
							"/musicContributor/list", "/musicContributor/*",
							"../../static/**",
							"/admin/user/changePassword"
							)
						.hasRole("READER")
					.antMatchers("/concert/video/*",
							"/contributor/list", "/contributor/*",
							"/concertWork/audioList/*", "/concertWorkTrack/audio/*", "/concertWork/video/*",
							"/admin/feedback/list", "/admin/feedback/new", "/admin/feedback/*",
							"/commitment/**", "/effort/**", "/task/list", "/task/*",
							"/reporting/**")
						.hasRole("USER")
					.antMatchers("/event/**", "/task/**")
						.hasAnyRole("ADMIN", "SUPERVISOR")
					.antMatchers("/ticketing/**",
							"concertSalesClass/**",
							"performanceContributorSalesClass/**",
							"seasonSalesClass/**",
							"seasonContributorSalesClass/**")
						.hasAnyRole("ADMIN", "TICKETER")
					.antMatchers("/admin/**", "/help/**", "/console/**")
						.hasRole("ADMIN")
					.antMatchers("/season/**",
							"/concert/**",
							"/performance/**",
							"/venue/**",
							"/contributor/**",
							"/work/**",
							"/concertWorkTrack/**",
							"/musicContributor/**",
							"/workMusicContributor/**",
							"/workMusic/**",
							"/concertWork/**",
							"/photo/**")
							.hasRole("ADMIN");

		http.csrf().disable();
		http.headers().frameOptions().disable();

	}

}
