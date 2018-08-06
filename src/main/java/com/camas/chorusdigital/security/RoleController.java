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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/admin/role")
public class RoleController {
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	private RoleService service;
	@Autowired
	public void setService(RoleService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("roles", service.list());

		return "roleList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		model.addAttribute("role", service.get(id));

		return "roleShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("roleObject", new Role());

		return "roleEdit";
	}

	@PostMapping("/save")
	public String save(Role roleObject, Model model) {

		service.save(roleObject);

		return "redirect:/admin/role/" + roleObject.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("roleObject", service.get(id));

		return "roleEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/admin/role/list";
	}


}
