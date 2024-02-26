package com.project.community.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.entity.NoticeEntityRepository;
import com.project.community.service.NoteService;
import com.project.community.service.NoticeService;

@Controller
public class NoticeController {

	@Autowired
	NoticeService nService;
	@Autowired
	NoticeEntityRepository noticeRepo;
	@Autowired
	private NoteService noteService;

	@GetMapping("/notice")
	public String notice(Model model, PageRequestDTO prDTO) {
		model.addAttribute("page", nService.getlist(prDTO));
		nService.noticeList(model);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "notice/notice";
	}

	@GetMapping("/notice/search")
	public String search(@RequestParam(value="keyword")String keyword, Model model) {
		List<NoticeDTO> nDTO = nService.search(keyword);
		model.addAttribute("keyList", nDTO);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "notice/notice-search";
	}
	
	@GetMapping("/notice-event")
	public String noticeEvent(Model model, PageRequestDTO prDTO) {
		model.addAttribute("page", nService.getlist(prDTO));
		nService.noticeList(model);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "notice/notice-event";
	}

	@GetMapping("/notice-notice")
	public String noticeNotice(Model model, PageRequestDTO prDTO) {
		model.addAttribute("page", nService.getlist(prDTO));
		nService.noticeList(model);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "notice/notice-notice";
	}

	@GetMapping("/add-notice")
	public String noticeAdd(Model model) {
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "notice/add-notice";
	}

	@PostMapping("/add-notice")
	public String noticeAdd(NoticeDTO noticeDTO, Authentication user) {
		nService.save(noticeDTO, user);
		return "redirect:/notice";
	}

	@GetMapping("/notice-detail/{no}")
	public String noticeDetail(@PathVariable("no") long no, Model model) {
		nService.noticeDetail(no, model);
		nService.getNotice(no);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "notice/notice-detail";
	}

	@GetMapping("/notice-edit/{no}")
	public String noticeEditPage(@PathVariable("no") long no, Model model) {
		nService.noticeDetail(no, model);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "notice/notice-edit";
	}

	@PutMapping("/notice-detail/{no}")
	public String noticeEdit(@PathVariable("no") long no, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("type") boolean type,
			@RequestParam("end") LocalDateTime end) {
		nService.noticeEdit(no, title, content, type, end);
		return "redirect:/notice";
	}

	@DeleteMapping("notice-detail/{no}")
	public String noticeDelete(@PathVariable("no") long no) {
		nService.noticeDelete(no);
		return "redirect:/notice";
	}
}
