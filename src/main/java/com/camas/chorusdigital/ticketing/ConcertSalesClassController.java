package com.camas.chorusdigital.ticketing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/concertSalesClass")
public class ConcertSalesClassController {
	private static final Logger log = LoggerFactory.getLogger(ConcertSalesClassController.class);

	private ConcertSalesClassService service;

	@Autowired
	public void setService(ConcertSalesClassService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("concertSalesClasses", service.list());

		return "concertSalesClassList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		ConcertSalesClass concertSalesClass = service.get(id);
		model.addAttribute("concertSalesClass", concertSalesClass);

		return "concertSalesClassShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		ConcertSalesClass concertSalesClass = new ConcertSalesClass();
		concertSalesClass.setConcert(service.getActiveConcert());

		model.addAttribute("concertSalesClass", concertSalesClass);

		return "concertSalesClassEdit";
	}

	@PostMapping("/save")
	public String save(@Valid ConcertSalesClass concertSalesClass, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "concertSalesClassEdit";
		}

		concertSalesClass = service.save(concertSalesClass);

		return "redirect:/concertSalesClass/" + concertSalesClass.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("concertSalesClass", service.get(id));

		return "concertSalesClassEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/concertSalesClass/list";
	}

}
