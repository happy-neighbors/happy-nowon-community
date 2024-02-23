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

import com.project.community.domain.dto.AnabadaDTO;
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.service.AnabadaService;
import com.project.community.service.NoteService;
import com.project.community.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AnabadaController {
	
	@Autowired
	private final AnabadaService anabadaService;
	@Autowired
	private NoteService noteService;
	
	private final FileUploadUtil fileUtil3;
	
	@GetMapping("/anabada")
	public String anabada(PageRequestDTO pageRequestDTO,Model model) {
		model.addAttribute("result", anabadaService.getList(pageRequestDTO));
		anabadaService.anabadaList(model);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "anabada/anabada";
	}
	@GetMapping("/add-anabada")
	public String addAnabada(Model model) {
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "anabada/add-anabada";
	}
	@ResponseBody
	@PostMapping("/files-temp-upload-anabada")
	public Map<String, String> fileTempUploadAnabada(@RequestParam(name = "goodsImg") MultipartFile driveFile) {
		return fileUtil3.s3TempUpload(driveFile);
	}
	@PostMapping("/add-anabada")
	public String addTown(AnabadaDTO dto, Authentication user) {
		anabadaService.anabadaSave(dto, user);
		System.out.println("저장완료");
		return "redirect:/anabada";
	}
	@GetMapping("/detail-anabada/{pk}")
	public String detailTown(@PathVariable(name = "pk") long pk, Model model) {
		anabadaService.detailAnabada(pk, model);
		anabadaService.getCount(pk);
		Long count = noteService.countState();
		model.addAttribute("count", count);
		return "anabada/detail-anabada";
	}
	@DeleteMapping("/detail-anabada/{pk}")
	public String deleteTown(@PathVariable(name = "pk") long pk) {
		anabadaService.deleteAnabada(pk);
		return "redirect:/anabada";
	}
	@PutMapping("/detail-anabada/{pk}")
	public String updateTown(@PathVariable(name = "pk") long pk, @RequestParam(value = "area") String area, @RequestParam(value = "content") String content, @RequestParam(value = "title") String title, @RequestParam(value = "state") String state) {
		anabadaService.updateAnabada(pk, area, content, title, state);
		return "redirect:/detail-anabada/{pk}";
	}

}
