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
@RequestMapping("/event")
public class EventController {
	private static final Logger log = LoggerFactory.getLogger(EventController.class);

	private EventService service;

	@Autowired
	public void setService(EventService service) {
		this.service = service;
	}

	//List

	@GetMapping("/list")
	public String list(Model model) {

		model.addAttribute("events", service.list());

		return "eventList";

	}

	@GetMapping("/listActive")
	public String listActive(Model model) {

		model.addAttribute("events", service.activeEvents());

		return "eventList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Event event = service.get(id);
		model.addAttribute("event", event);

		return "eventShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("event", new Event());
		model.addAttribute("types", service.getEventTypes());
		model.addAttribute("seasons", service.getEventSeasons());
		model.addAttribute("concerts", service.getEventConcerts());
		model.addAttribute("performances", service.getEventPerformances());

		return "eventEdit";
	}

	@PostMapping("/save")
	public String save(@Valid Event event, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "eventEdit";
		}

		event = service.save(event);

		return "redirect:/event/" + event.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("event", service.get(id));
		model.addAttribute("types", service.getEventTypes());
		model.addAttribute("seasons", service.getEventSeasons());
		model.addAttribute("concerts", service.getEventConcerts());
		model.addAttribute("performances", service.getEventPerformances());

		return "eventEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/event/list";
	}

	//Deactivate

	@GetMapping("/deactivate/{id}")
	public String deactivate(@PathVariable Long id) {

		Event event = service.get(id);
		service.deactivate(event);

		return "redirect:/event/list";
	}

}
