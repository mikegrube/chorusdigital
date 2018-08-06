package com.camas.chorusdigital.program;

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
@RequestMapping("/workMusicContributor")
public class WorkMusicContributorController {
	private static final Logger log = LoggerFactory.getLogger(WorkMusicContributorController.class);

	private static String flash = null;

	private WorkMusicContributorService service;
	@Autowired
	public void setService(WorkMusicContributorService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(Model model) {

		model.addAttribute("workMusicContributors", service.list());

		return "workMusicContributorList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		WorkMusicContributor workMusicContributor = service.get(id);
		model.addAttribute("workMusicContributor", workMusicContributor);

		return "workMusicContributorShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("workMusicContributor", new WorkMusicContributor());
		model.addAttribute("works", service.availableWorks());
		model.addAttribute("musicContributors", service.availableMusicContributors());
		model.addAttribute("workMusicContributorRoles", service.availableWorkMusicContributorRoles());

		return "workMusicContributorEdit";
	}

	@GetMapping("/new/{workId}")
	public String createWithWork(@PathVariable Long workId, Model model) {

		Work work = service.getWork(workId);
		WorkMusicContributor workMusicContributor = new WorkMusicContributor();
		workMusicContributor.setWork(work);
		model.addAttribute("workMusicContributor", workMusicContributor);
		model.addAttribute("works", service.availableWorks());
		model.addAttribute("musicContributors", service.availableMusicContributors());
		model.addAttribute("workMusicContributorRoles", service.availableWorkMusicContributorRoles());

		return "workMusicContributorEdit";
	}

	@PostMapping("/save")
	public String saveWorkMusic(@Valid WorkMusicContributor workMusicContributor, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("works", service.availableWorks());
			model.addAttribute("musicContributors", service.availableMusicContributors());
			model.addAttribute("workMusicContributorRoles", service.availableWorkMusicContributorRoles());
			return "workMusicContributorEdit";
		}

		workMusicContributor = service.save(workMusicContributor);

		Work work = workMusicContributor.getWork();

		return "redirect:/work/" + work.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("workMusicContributor", service.get(id));
		model.addAttribute("works", service.availableWorks());
		model.addAttribute("musicContributors", service.availableMusicContributors());
		model.addAttribute("workMusicContributorRoles", service.availableWorkMusicContributorRoles());

		return "workMusicContributorEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		WorkMusicContributor workMusicContributor = service.get(id);
		Work work = workMusicContributor.getWork();

		service.delete(id);

		return "redirect:/work/" + work.getId();
	}

}