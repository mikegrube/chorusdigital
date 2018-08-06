package com.camas.chorusdigital.ticketing;

import com.camas.chorusdigital.security.PasswordChange;
import com.camas.chorusdigital.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "SameReturnValue"})
@Controller
@RequestMapping("/ticketing")
public class TicketingController {
	private static final Logger log = LoggerFactory.getLogger(TicketingController.class);

	@GetMapping("/index")
	public String ticketing(Model model) {

		return "ticketingIndex";
	}

}