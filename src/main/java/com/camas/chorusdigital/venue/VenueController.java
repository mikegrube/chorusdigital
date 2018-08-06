package com.camas.chorusdigital.venue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/venue")
public class VenueController {

	private VenueService service;
	@Autowired
	public void setService(VenueService service) {
		this.service = service;
	}

	@Value("${google.api.key}")
	private String googleApiKey;

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("venues", service.list());

		return "venueList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Venue venue = service.get(id);
		model.addAttribute("venue", venue);
		model.addAttribute("venuePerformances", service.performancesForVenue(venue));

		String mapURL = "https://www.google.com/maps/embed/v1/place?q=" + venue.getMapURL() + "&" + "key=" + googleApiKey;
		model.addAttribute("mapURL", mapURL);
		return "venueShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("venue", new Venue());

		return "venueEdit";
	}

	@PostMapping("/save")
	public String save(@Valid Venue venue, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "venueEdit";
		}

		service.save(venue);

		return "redirect:/venue/" + venue.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("venue", service.get(id));

		return "venueEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/venue/list";
	}

}