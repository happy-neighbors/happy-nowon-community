package com.project.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TownController {
	
	@GetMapping("/town")
	public String town() {
		return "town/town";
	}
	@GetMapping("/add-town")
	public String addTown() {
		return "town/add-town";
	}

}
