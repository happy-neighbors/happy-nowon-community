package com.project.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.community.service.TownService;
import com.project.community.domain.entity.TownEntity;
import com.project.community.service.AnabadaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
	
	@Autowired
	private TownService townService;
	@Autowired
	private AnabadaService AnabadaService;
	
	@GetMapping("/")
	public String index(Model model) {
		List<TownEntity> top5 = townService.getTop5Procedures();
		model.addAttribute("top5", top5);
		return "index";
	}

}
