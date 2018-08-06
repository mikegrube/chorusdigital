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
@RequestMapping("/seasonSalesClass")
public class SeasonSalesClassController {
	private static final Logger log = LoggerFactory.getLogger(SeasonSalesClassController.class);

	private SeasonSalesClassService service;

	@Autowired
	public void setService(SeasonSalesClassService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("seasonSalesClasses", service.list());

		return "seasonSalesClassList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		SeasonSalesClass seasonSalesClass = service.get(id);
		model.addAttribute("seasonSalesClass", seasonSalesClass);

		return "seasonSalesClassShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		SeasonSalesClass seasonSalesClass = new SeasonSalesClass();
		seasonSalesClass.setSeason(service.getActiveSeason());
		model.addAttribute("seasonSalesClass", seasonSalesClass);

		return "seasonSalesClassEdit";
	}

	@PostMapping("/save")
	public String save(@Valid SeasonSalesClass seasonSalesClass, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "seasonSalesClassEdit";
		}

		seasonSalesClass = service.save(seasonSalesClass);

		return "redirect:/seasonSalesClass/" + seasonSalesClass.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("seasonSalesClass", service.get(id));

		return "seasonSalesClassEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/seasonSalesClass/list";
	}

}
