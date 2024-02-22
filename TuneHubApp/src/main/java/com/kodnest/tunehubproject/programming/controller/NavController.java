package com.kodnest.tunehubproject.programming.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {
	/*We cannot directly send the control from one html page to another html page , it is done using mapping and mapping is done
	 																											in the controller class , so we created a NavController for mappings*/
	@GetMapping("/map-register")
	public String registerMapping() {
		return "register";

	}

	@GetMapping("/map-login")
	public String loginMapping() {
		return "login";

	}

	@GetMapping("/map-songs")
	public String songsMapping() {
		return "addsongs";

	}

	@GetMapping("/map-pay")
	public String payMapping() {
		return "samplePayment";

	}

}
