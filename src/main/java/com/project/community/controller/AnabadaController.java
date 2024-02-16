package com.project.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnabadaController {
	
	@GetMapping("/anabada")
	public String anabada() {
		return "anabada/anabada";
	}
	@GetMapping("/add-anabada")
	public String addAnabada() {
		return "anabada/add-anabada";
	}

}
