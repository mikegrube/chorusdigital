package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.security.User;
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
@RequestMapping("/commitment")
public class CommitmentController {
	private static final Logger log = LoggerFactory.getLogger(CommitmentController.class);

	private CommitmentService service;
	@Autowired
	public void setService(CommitmentService service) {
		this.service = service;
	}

	//FIXME: Info holder for edit; we should carry this through the edit in the commitment structure
	private static Task currentTask;
	private static User currentVolunteer;

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("commitments", service.list());

		return "commitmentList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Commitment commitment = service.get(id);
		model.addAttribute("commitment", commitment);
		Iterable<Effort> efforts = service.findEffortsForCommitment(commitment);
		model.addAttribute("efforts", efforts);

		return "commitmentShow";

	}

	//Create

	@GetMapping("/new/{taskId}")
	public String create(@PathVariable Long taskId, Model model) {

		Commitment commitment = new Commitment();
		currentTask = service.findTask(taskId);
		commitment.setTask(currentTask);
		currentVolunteer = null;
		model.addAttribute("commitment", commitment);
		model.addAttribute("volunteers", service.findAvailableVolunteers());

		return "commitmentEdit";
	}

	@GetMapping("/newWithVolunteer/{taskId}")
	public String createWithVolunteer(@PathVariable Long taskId, Model model) {

		Commitment commitment = new Commitment();
		currentTask = service.findTask(taskId);
		commitment.setTask(currentTask);
		currentVolunteer = service.findCurrentUser();
		commitment.setUser(currentVolunteer);

		model.addAttribute("commitment", commitment);

		return "commitmentWithVolunteerEdit";
	}

	@PostMapping("/save")
	public String save(@Valid Commitment commitment, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "commitmentEdit";
		}

		commitment.setTask(currentTask);
		if (currentVolunteer != null) {
			commitment.setUser(currentVolunteer);
		}
		commitment = service.save(commitment);

		return "redirect:/task/" + currentTask.getId();
	}
/*
	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("commitment", service.get(id));

		return "commitmentEdit";
	}
*/
	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		Commitment commitment = service.get(id);
		Long taskId = commitment.getTask().getId();

		service.delete(id);

		return "redirect:/task/" + taskId;
	}

}
