package com.project.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.community.domain.dto.NoteDTO;
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.service.NoteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoteController {
	
	@Autowired
	private final NoteService noteService;
	
	@GetMapping("/take-note")
	public String takeNote(PageRequestDTO pageRequestDTO,Model model) {
		model.addAttribute("result", noteService.getList(pageRequestDTO));
		noteService.takeNoteList(model);
		return "note/take-note";
	}
	@GetMapping("/send-note")
	public String sendNote(PageRequestDTO pageRequestDTO,Model model) {
		model.addAttribute("result", noteService.getList(pageRequestDTO));
		noteService.sendNoteList(model);
		return "note/send-note";
	}
	@GetMapping("/write-note")
	public String writeNote() {
		return "note/write-note";
	}
	@PostMapping("/send-note")
	public String sendGoNote(NoteDTO dto) {
		noteService.noteSave(dto);
		return "redirect:/take-note";
	}
	@GetMapping("/detail-note/{pk}")
	public String detailNote(@PathVariable(name = "pk") long pk, Model model) {
		noteService.detailNote(pk, model);
		noteService.getState(pk);
		return "note/detail-note";
	}

}
