package com.camas.chorusdigital.program;

import com.camas.chorusdigital.concert.Concert;
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
@RequestMapping("/concertWork")
public class ConcertWorkController {
	private static final Logger log = LoggerFactory.getLogger(ConcertWorkController.class);

	private static String flash = null;

	private ConcertWorkService service;
	@Autowired
	public void setService(ConcertWorkService service) {
		this.service = service;
	}

	//List

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public String list(Model model) {

		model.addAttribute("concertWorks", service.list());

		return "concertWorkList";

	}

	//Show

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		ConcertWork concertWork = service.get(id);
		model.addAttribute("concertWork", concertWork);
		model.addAttribute("concertWorkTracks", service.tracksForConcertWork(concertWork));

		return "concertWorkShow";

	}

	//Create

	@GetMapping("/new")
	public String create(Model model) {

		model.addAttribute("concertWork", new ConcertWork());
		model.addAttribute("works", service.availableWorks());
		model.addAttribute("concerts", service.availableConcerts());

		return "concertWorkEdit";
	}

	@GetMapping("/newWithConcert/{concertId}")
	public String create(@PathVariable Long concertId, Model model) {

		Concert concert = service.findConcert(concertId);
		ConcertWork concertWork = new ConcertWork();
		concertWork.setConcert(concert);
		model.addAttribute("concertWork", concertWork);
		model.addAttribute("works", service.availableWorks());
		model.addAttribute("concerts", service.availableConcerts());

		return "concertWorkEdit";
	}

	@PostMapping("/save")
	public String saveConcert(@Valid ConcertWork concertWork, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("works", service.availableWorks());
			model.addAttribute("concerts", service.availableConcerts());
			return "concertWorkEdit";
		}

		service.save(concertWork);

		return "redirect:/concertWork/" + concertWork.getId();
	}

	//Update

	@GetMapping("/edit/{id}")
	public String update(@PathVariable Long id, Model model) {

		model.addAttribute("concertWork", service.get(id));
		model.addAttribute("works", service.availableWorks());
		model.addAttribute("concerts", service.availableConcerts());

		return "concertWorkEdit";
	}

	//Uses the same POST as create

	//Delete

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/concertWork/list";
	}

	@GetMapping("/audioList/{concertWorkId}")
	public String displayAudioList(@PathVariable Long concertWorkId, Model model) {

		ConcertWork concertWork = service.get(concertWorkId);
		model.addAttribute("title", concertWork.getWork().getTitle());
		Iterable<ConcertWorkTrack> concertWorkTracks = service.tracksForConcertWork(concertWork);

		int ct = 0;
		ConcertWorkTrack first = null;
		for (ConcertWorkTrack concertWorkTrack : concertWorkTracks) {
			ct++;
			if (ct == 1) {
				first = concertWorkTrack;
			}
		}
		if (ct == 1) {
			return "redirect:/concertWorkTrack/audio/" + first.getId();
		}

		model.addAttribute("concertWorkTracks", service.tracksForConcertWork(concertWork));

		return "concertWorkTrackList";
	}

	@GetMapping("/video/{id}")
	public String displayVideo(@PathVariable Long id, Model model) {

		ConcertWork concertWork = service.get(id);
		String url = concertWork.getVideoURL();
		if (!url.startsWith("http://")  && !url.startsWith("https://")) {
			url = "http://" + url;
		}

		return "redirect:" + url;
	}
}