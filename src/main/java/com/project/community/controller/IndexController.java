package com.project.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.community.service.TownService;
import com.project.community.domain.entity.AnabadaEntity;
import com.project.community.domain.entity.TownEntity;
import com.project.community.domain.entity.NoticeEntity;
import com.project.community.service.AnabadaService;
import com.project.community.service.NoteService;
import com.project.community.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {
	
	@Autowired
	private TownService townService;
	@Autowired
	private AnabadaService AnabadaService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/")
	public String index(Model model) {
		List<TownEntity> top5 = townService.getTop5Procedures();
		model.addAttribute("top5", top5);
		List<AnabadaEntity> top52 = AnabadaService.getTop5Procedures();
		model.addAttribute("top52", top52);
		List<NoticeEntity> top53 = noticeService.getTop5Procedures();
		model.addAttribute("top53", top53);
		noteService.takeNoteList(model);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "index";
	}

}
