package com.camas.chorusdigital.help;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/help")
public class HelpController {

	private HelpService service;
	@Autowired
	public void setService(HelpService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("helps", service.list());

		return "helpList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Help help = service.get(id);
		model.addAttribute("help", help);

		return "helpShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		Help help = new Help();
		model.addAttribute("help", help);

		return "helpEdit";
	}

	@PostMapping("/save")
	public String save(Help help, Model model) {

		service.save(help);

		return "redirect:/help/" + help.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("help", service.get(id));

		return "helpEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/help/list";
	}

}
