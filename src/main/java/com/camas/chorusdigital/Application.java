package com.camas.chorusdigital;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

	@Bean
	ServletRegistrationBean h2servletRegistration(){
		ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

//Invocation for development:
//	gradle -Dspring.profiles.active=dev bootRun

//Shutdown for development:
//  curl -X POST http://localhost:port/shutdown

//Installation as a Linux service:
//	https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html

