package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.contributor.Contributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SuppressWarnings({"FieldCanBeLocal", "SameReturnValue"})
@Controller
@RequestMapping("/concertContributor")
public class ConcertContributorController {
	private static final Logger log = LoggerFactory.getLogger(ConcertContributorController.class);

	private static String flash = null;

	private ConcertContributorService service;
	@Autowired
	public void setService(ConcertContributorService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(Model model) {

		model.addAttribute("concertContributors", service.list());

		return "concertContributorList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		ConcertContributor concertContributor = service.get(id);
		model.addAttribute("concertContributor", concertContributor);

		return "concertContributorShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("concertContributor", new ConcertContributor());
		model.addAttribute("contributors", service.availableContributors());
		model.addAttribute("concerts", service.availableConcerts());
		model.addAttribute("concertContributorRoles", service.availableConcertContributorRoles());

		return "concertContributorEdit";
	}

	@GetMapping("/newWithConcert/{concertId}")
	public String create(@PathVariable Long concertId, Model model) {

		Concert concert = service.findConcert(concertId);
		ConcertContributor concertContributor = new ConcertContributor();
		concertContributor.setConcert(concert);

		model.addAttribute("concertContributor", concertContributor);
		model.addAttribute("contributors", service.availableContributors());
		model.addAttribute("concerts", service.availableConcerts());
		model.addAttribute("concertContributorRoles", service.availableConcertContributorRoles());

		return "concertContributorEdit";
	}

	@PostMapping("/save")
	public String saveConcert(@Valid ConcertContributor concertContributor, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("contributors", service.availableContributors());
			model.addAttribute("concerts", service.availableConcerts());
			model.addAttribute("concertContributorRoles", service.availableConcertContributorRoles());
			return "concertContributorEdit";
		}

		service.save(concertContributor);

		return "redirect:/concertContributor/" + concertContributor.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("concertContributor", service.get(id));
		model.addAttribute("contributors", service.availableContributors());
		model.addAttribute("concerts", service.availableConcerts());
		model.addAttribute("concertContributorRoles", service.availableConcertContributorRoles());

		return "concertContributorEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/concertContributor/list";
	}

}