package com.camas.chorusdigital.volunteering;

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
@RequestMapping("/effort")
public class EffortController {
	private static final Logger log = LoggerFactory.getLogger(EffortController.class);

	private EffortService service;
	@Autowired
	public void setService(EffortService service) {
		this.service = service;
	}

	private static Commitment currentCommitment;

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("efforts", service.list());

		return "effortList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Effort effort = service.get(id);
		model.addAttribute("effort", effort);

		return "effortShow";

	}

	//Create

	@GetMapping("/new/{commitmentId}")
	public String create(@PathVariable Long commitmentId, Model model) {

		currentCommitment = service.findCommitment(commitmentId);
		Effort effort = new Effort();
		effort.setCommitment(currentCommitment);
		model.addAttribute("effort", effort);

		return "effortEdit";
	}

	@PostMapping("/save")
	public String save(@Valid Effort effort, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "effortEdit";
		}

		effort.setCommitment(currentCommitment);

		effort = service.save(effort);

		Long taskId = currentCommitment.getTask().getId();

		return "redirect:/task/" + taskId;
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		Effort effort = service.get(id);
		model.addAttribute("effort", effort);
		currentCommitment = effort.getCommitment();

		return "effortEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		Long taskId = service.get(id).getCommitment().getTask().getId();

		service.delete(id);

		return "redirect:/task/" + taskId;
	}

}
