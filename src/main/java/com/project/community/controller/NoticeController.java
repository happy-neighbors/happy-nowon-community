package com.project.community.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

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
	public String noticeAdd(NoticeDTO noticeDTO, Authentication user) {
		nService.save(noticeDTO, user);
		return "redirect:/notice";
	}
	
	@GetMapping("/notice-detail/{no}")
	public String noticeDetail(@PathVariable("no")long no, Model model) {
		nService.noticeDetail(no, model);
		return "notice/notice-detail";
	}
	
	@GetMapping("/notice-edit/{no}")
	public String noticeEditPage(@PathVariable("no")long no, Model model) {
		nService.noticeDetail(no, model);
		return "notice/notice-edit";
	}
	
	@PutMapping("/notice-detail/{no}")
	public String noticeEdit(@PathVariable("no")long no, 
			@RequestParam("title")String title, 
			@RequestParam("content")String content, 
			@RequestParam("type")boolean type, 
			@RequestParam("end")LocalDateTime end) {
		nService.noticeEdit(no, title, content, type, end);
		return "redirect:/notice";
	}
	
	@DeleteMapping("notice-detail/{no}")
	public String noticeDelete(@PathVariable("no")long no) {
		nService.noticeDelete(no);
		return "redirect:/notice";
	}
}
