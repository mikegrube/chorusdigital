package com.camas.chorusdigital.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/admin/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private String holdEncryptedPassword = null; 	//So we don't have to send it over the network

	private UserService service;
	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("users", service.list());

		return "userList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		User user = service.get(id);
		model.addAttribute("user", user);

		return "userShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("allRoles", service.availableRoles());
		model.addAttribute("members", service.availableMembers());

		holdEncryptedPassword = null;

		return "userEdit";
	}

	@PostMapping("/save")
	public String save(@Valid User user, Model model, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("allRoles", service.availableRoles());
			model.addAttribute("members", service.availableMembers());
			return "userEdit";
		}

		if (holdEncryptedPassword != null) {
			user.setEncryptedPassword(holdEncryptedPassword);
			holdEncryptedPassword = null;		//Don't leave it hanging around
		}

		service.save(user);

		return "redirect:/admin/user/" + user.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		User user = service.get(id);
		holdEncryptedPassword = user.getEncryptedPassword();	//Save this since it's not on the page

		model.addAttribute("user", user);
		model.addAttribute("allRoles", service.availableRoles());
		model.addAttribute("members", service.availableMembers());

		return "userEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/admin/user/list";
	}

	@GetMapping("/changePassword")
	public String changePasswordRequest(Model model) {

		PasswordChange passwordChange = new PasswordChange();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			passwordChange.setUser(auth.getName());
		} else {
			return "redirect:/";
		}
		passwordChange.setMessage(null);

		model.addAttribute("passwordChange", passwordChange);

		return "changePassword";
	}

	@PostMapping("/changePassword")
	public String changePassword(PasswordChange passwordChange, Model model) {

		if (!passwordChange.getPassword().equals(passwordChange.getPassword2())) {
			passwordChange.setMessage("Passwords must match");
			model.addAttribute("passwordChange", passwordChange);
			return "changePassword";
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = service.findByUserName(auth.getName());
		user.setPassword(passwordChange.getPassword());
		user = service.save(user);

		return "redirect:/";
	}

	@GetMapping("/register")
	public String register(Model model) {

		RegistrationRequest registrationRequest = new RegistrationRequest();
		model.addAttribute("registrationRequest", registrationRequest);

		return "userRegistration";
	}

	@PostMapping("/register")
	public String register(@Valid RegistrationRequest registrationRequest, BindingResult bindingResult, Model model) {

		User user = null;
		if (!bindingResult.hasErrors()) {

			String result = service.checkForNameOrEmail(registrationRequest);
			if (result != null) {

				if (result.equals("email")) {
					bindingResult.rejectValue("email", "email.registered", "User email is already registered.");
				}
				if (result.equals("userName")) {
					bindingResult.rejectValue("userName", "name.registered","User name is already registered.");
				}

				model.addAttribute("registrationRequest", registrationRequest);
				return "userRegistration";

			} else {

				if (!registrationRequest.getPassword().equals(registrationRequest.getPassword2())) {
					bindingResult.rejectValue("password2", "password.mismatch", "Password confirmation must match password.");

					model.addAttribute("registrationRequest", registrationRequest);
					return "userRegistration";
				}

				if (!registrationRequest.isTermsAgreed()) {
					bindingResult.rejectValue("termsAgreed", "terms.not.agreed", "You must agree to the terms.");

					model.addAttribute("registrationRequest", registrationRequest);
					return "userRegistration";
				}

				user = service.registerUser(registrationRequest);

				model.addAttribute("flash", "User registered. Please log in.");
				return "redirect:/login";
			}
		} else {
			model.addAttribute("registrationRequest", registrationRequest);
			return "userRegistration";
		}

	}
}
