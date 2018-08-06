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
@RequestMapping("/concertWorkTrack")
public class ConcertWorkTrackController {
	private static final Logger log = LoggerFactory.getLogger(ConcertWorkTrackController.class);

	private static String flash = null;

	private ConcertWorkTrackService service;
	@Autowired
	public void setService(ConcertWorkTrackService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(Model model) {

		model.addAttribute("concertWorkTracks", service.list());

		return "concertWorkTrackList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		ConcertWorkTrack concertWorkTrack = service.get(id);
		model.addAttribute("concertWorkTrack", concertWorkTrack);

		return "concertWorkTrackShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("concertWorkTrack", new ConcertWorkTrack());
		model.addAttribute("concertWorks", service.availableConcertWorks());

		return "concertWorkTrackEdit";
	}

	@GetMapping("/newWithConcertWork/{concertWorkId}")
	public String create(@PathVariable Long concertWorkId, Model model) {

		ConcertWorkTrack concertWorkTrack = new ConcertWorkTrack();
		ConcertWork concertWork = service.findConcertWork(concertWorkId);
		concertWorkTrack.setTitle(concertWork.getWork().getTitle());
		concertWorkTrack.setConcertWork(concertWork);
		model.addAttribute("concertWorkTrack", concertWorkTrack);
		model.addAttribute("audioFiles", service.audioFilesForConcert(concertWork.getConcert()));

		return "concertWorkTrackEdit";
	}

	@PostMapping("/save")
	public String saveConcert(@Valid ConcertWorkTrack concertWorkTrack, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("concertWorks", service.availableConcertWorks());
			return "concertWorkTrackEdit";
		}

		service.save(concertWorkTrack);

		return "redirect:/concertWork/" + concertWorkTrack.getConcertWork().getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		ConcertWorkTrack concertWorkTrack = service.get(id);
		model.addAttribute("concertWorkTrack", concertWorkTrack);
		model.addAttribute("concertWorks", service.availableConcertWorks());
		model.addAttribute("audioFiles", service.audioFilesForConcert(concertWorkTrack.getConcertWork().getConcert()));

		return "concertWorkTrackEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/concertWorkTrack/list";
	}

	@GetMapping("/audio/{concertWorkTrackId}")
	public String displayAudio(@PathVariable Long concertWorkTrackId, Model model) {

		ConcertWorkTrack concertWorkTrack = service.get(concertWorkTrackId);
		model.addAttribute("concertWorkTrack", concertWorkTrack);

		return "audioPage";
	}

}