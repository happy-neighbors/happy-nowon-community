package com.project.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.community.domain.dto.NoticeDTO;
import com.project.community.domain.entity.NoticeEntityRepository;
import com.project.community.service.NoticeService;

@Controller
public class NoticeController {

	@Autowired
	NoticeService nService;
	@Autowired
	NoticeEntityRepository noticeRepo;
	
	@GetMapping("/notice")
	public String notice(Model model) {
		nService.noticeList(model);
		return "notice/notice";
	}
	
	@GetMapping("/add-notice")
	public String noticeAdd() {
		return "notice/add-notice";
	}
	
	@PostMapping("/add-notice")
	public String noticeAdd(NoticeDTO noticeDTO) {
		nService.save(noticeDTO);
		return "redirect:/notice";
	}
}
