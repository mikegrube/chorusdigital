package com.camas.chorusdigital;

import com.camas.chorusdigital.utility.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("SameReturnValue")
@Controller
public class IndexController {

	private UtilityService utilityService;
	@Autowired
	public void setUtilityService(UtilityService utilityService) {
		this.utilityService = utilityService;
	}

	@GetMapping("/")
	String index(Model model){

		String picURL = utilityService.getSplashPic();
		model.addAttribute("picURL", picURL);

		return "index";
	}

	@GetMapping("/stop")
	String stopping() { return "shuttingdown"; }

	@GetMapping("/login")
	public String login(){
		return "login";
	}

	@GetMapping("/about")
	public String about(Model model) {

		File aboutFile = utilityService.getResourceFile("aboutText.html");
		String aboutText = null;
		try {
			aboutText = new String(Files.readAllBytes(Paths.get(aboutFile.getAbsolutePath())));
		} catch (IOException e) {
			aboutText = "The About page requires an aboutText.html file in the cdrepository directory";
		}
		model.addAttribute("aboutText", aboutText);

		return "about"; }

}
