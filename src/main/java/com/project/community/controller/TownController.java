package com.project.community.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.TownCommentDTO;
import com.project.community.domain.dto.TownDTO;
import com.project.community.service.NoteService;
import com.project.community.service.TownService;
import com.project.community.utils.FileUploadUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TownController {
	
	@Autowired
	private final TownService townService;
	@Autowired
	private final NoteService noteService;
	
	private final FileUploadUtil fileUtil3;
	
	@GetMapping("/town")
	public String town(PageRequestDTO pageRequestDTO,Model model) {
		model.addAttribute("result", townService.getList(pageRequestDTO));
		townService.townList(model);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "town/town";
	}
	@GetMapping("/add-town")
	public String addTown(Model model) {
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "town/add-town";
	}
	@ResponseBody
	@PostMapping("/files-temp-upload")
	public Map<String, String> fileTempUpload(@RequestParam(name = "goodsImg") MultipartFile driveFile) {
		return fileUtil3.s3TempUpload(driveFile);
	}
	@PostMapping("/add-town")
	public String addTown(TownDTO dto, Authentication user) {
		townService.townSave(dto, user);
		System.out.println("저장완료");
		return "redirect:/town";
	}
	@GetMapping("/detail-town/{pk}")
	public String detailTown(@PathVariable(name = "pk") long pk, Model model) {
		townService.detailTown(pk, model);
		townService.getCount(pk);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "town/detail-town";
	}
	@DeleteMapping("/detail-town/{pk}")
	public String deleteTown(@PathVariable(name = "pk") long pk) {
		townService.deleteTown(pk);
		return "redirect:/town";
	}
	@PutMapping("/detail-town/{pk}")
	public String updateTown(@PathVariable(name = "pk") long pk, @RequestParam(value = "area") String area, @RequestParam(value = "content") String content, @RequestParam(value = "title") String title) {
		townService.updateTown(pk, area, content, title);
		return "redirect:/detail-town/{pk}";
	}
	

}
