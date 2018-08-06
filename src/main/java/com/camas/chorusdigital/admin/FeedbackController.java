package com.camas.chorusdigital.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/admin/feedback")
public class FeedbackController {

	private FeedbackService service;
	@Autowired
	public void setService(FeedbackService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("feedbacks", service.list());

		return "feedbackList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Feedback feedback = service.get(id);
		model.addAttribute("feedback", feedback);

		return "feedbackShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		Feedback feedback = new Feedback();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			feedback.setUser(auth.getName());
		}
		feedback.setDateTime(new Date());
		model.addAttribute("feedback", feedback);

		return "feedbackEdit";
	}

	@PostMapping("/save")
	public String save(Feedback feedback, Model model) {

		service.save(feedback);

		return "redirect:/admin/feedback/" + feedback.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("feedback", service.get(id));

		return "feedbackEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/admin/feedback/list";
	}

}
