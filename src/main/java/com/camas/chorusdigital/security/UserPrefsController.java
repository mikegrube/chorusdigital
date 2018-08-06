package com.camas.chorusdigital.security;

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
@RequestMapping("/admin/userPrefs")
public class UserPrefsController {
	private static final Logger log = LoggerFactory.getLogger(UserPrefsController.class);

	private UserPrefsService service;

	@Autowired
	public void setService(UserPrefsService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("userPrefss", service.list());

		return "userPrefsList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		UserPrefs userPrefs = service.get(id);
		model.addAttribute("userPrefs", userPrefs);

		return "userPrefsShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("userPrefs", new UserPrefs());
		model.addAttribute("taskListStyles", service.availableTaskListStyles());

		return "userPrefsEdit";
	}

	@PostMapping("/save")
	public String save(@Valid UserPrefs userPrefs, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "userPrefsEdit";
		}

		userPrefs = service.save(userPrefs);

		return "redirect:/userPrefs/" + userPrefs.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("userPrefs", service.get(id));
		model.addAttribute("taskListStyles", service.availableTaskListStyles());

		return "userPrefsEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/userPrefs/list";
	}

}
