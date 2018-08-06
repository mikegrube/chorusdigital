package com.camas.chorusdigital.errorhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"FieldCanBeLocal", "SameReturnValue"})
@Controller
public class ErrorController {
	private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

	@GetMapping("/error")
	String error(HttpServletRequest request, Model model){

		model.addAttribute("error", request.getAttribute("error"));
		model.addAttribute("status", request.getAttribute("status"));
		model.addAttribute("message", request.getAttribute("message"));

		return "error";

	}

}