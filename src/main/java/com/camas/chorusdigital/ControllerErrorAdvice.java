package com.camas.chorusdigital;

import com.camas.chorusdigital.security.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ControllerErrorAdvice {
	private static final Logger log = LoggerFactory.getLogger(ControllerErrorAdvice.class);

	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public String databaseError(Exception exception) {
		// Nothing to do. Return value 'databaseError' used as logical view name
		// of an error page, passed to view-resolver(s) in usual way.
		log.error("Request raised " + exception.getClass().getSimpleName());

		return "databaseError";
	}

}
