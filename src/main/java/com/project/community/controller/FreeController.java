package com.project.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FreeController {
	
	@GetMapping("/free")
	public String free() {
		return "free/free";
	}
	@GetMapping("/add-free")
	public String addFree() {
		return "free/add-free";
	}

}
