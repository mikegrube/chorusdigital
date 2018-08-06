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
@RequestMapping("/task")
public class TaskController {
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);

	private TaskService service;

	@Autowired
	public void setService(TaskService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("tasks", service.list());

		return "taskList";

	}

	@GetMapping("/listAvailable")
	public String listAvailable(Model model) {

		model.addAttribute("tasks", service.listAvailable());

		return "taskList";

	}

	@GetMapping("/listAll")
	public String listAll(Model model) {

		model.addAttribute("tasks", service.listAll());

		return "taskList";

	}

	@GetMapping("/listActive")
	public String listActive(Model model) {

		model.addAttribute("tasks", service.listActive());

		return "taskList";

	}

	@GetMapping("/listForVolunteer")
	public String listForVolunteer(Model model) {

		model.addAttribute("tasks", service.listForVolunteer());

		return "taskList";

	}

	@GetMapping("/listAlerted")
	public String listAlerted(Model model) {

		model.addAttribute("tasks", service.listAlerted());

		return "taskList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Task task = service.get(id);
		model.addAttribute("task", task);
		model.addAttribute("commitments", service.commitmentsForTask(task));

		return "taskShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("task", new Task());
		model.addAttribute("taskUnits", service.availableTaskUnits());
		model.addAttribute("events", service.availableEvents());

		return "taskEdit";
	}

	@PostMapping("/save")
	public String save(@Valid Task task, BindingResult bindingResult, Model model) {

		boolean dateOk = task.convertEventDate();
		boolean timeOk = task.convertEventTime();

		if (bindingResult.hasErrors()) {
			model.addAttribute("task", task);
			model.addAttribute("taskUnits", service.availableTaskUnits());
			model.addAttribute("events", service.availableEvents());
			return "taskEdit";
		} else {
			if (!dateOk) {
				bindingResult.rejectValue("eventDateString", "eventDate.invalid", "Event date is invalid. Format is two-digit-month/two-digit day/4-digit year.");
				model.addAttribute("task", task);
				model.addAttribute("taskUnits", service.availableTaskUnits());
				model.addAttribute("events", service.availableEvents());
				return "taskEdit";
			}

			if (!timeOk) {
				bindingResult.rejectValue("eventTimeString", "eventTime.invalid", "Event time is invalid. Format is two-digit hour (00-23):two-digit minutes.");
				model.addAttribute("task", task);
				model.addAttribute("taskUnits", service.availableTaskUnits());
				model.addAttribute("events", service.availableEvents());
				return "taskEdit";
			}

		}

		task = service.save(task);

		return "redirect:/task/" + task.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("task", service.get(id));
		model.addAttribute("taskUnits", service.availableTaskUnits());
		model.addAttribute("events", service.availableEvents());

		return "taskEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/task/list";
	}

	//Deactivate

	@GetMapping("/deactivate/{id}")
	public String deactivate(@PathVariable Long id) {

		Task task = service.get(id);
		service.deactivate(task);

		return "redirect:/task/list";
	}

}
