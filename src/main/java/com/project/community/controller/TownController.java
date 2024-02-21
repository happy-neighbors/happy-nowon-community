package com.project.community.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.community.domain.dto.TownDTO;
import com.project.community.service.TownService;
import com.project.community.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TownController {
	
	@Autowired
	private final TownService townService;
	
	private final FileUploadUtil fileUtil3;
	
	@GetMapping("/town")
	public String town(TownDTO townDTO, Model model) {
		townService.townList(model);
		return "town/town";
	}
	@GetMapping("/add-town")
	public String addTown() {
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
		
		return "redirect:/town";
	}
	@GetMapping("/detail-town/{pk}")
	public String detailTown(@PathVariable(name = "pk") long pk, Model model) {
		townService.detailTown(pk, model);
		return "town/detail-town";
	}
	
	

}
