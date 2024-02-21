package com.project.community.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.community.domain.dto.NoticeDTO;
import com.project.community.domain.entity.NoticeEntity;
import com.project.community.domain.entity.NoticeEntityRepository;
import com.project.community.service.NoticeService;

@Service
public class NoticeServiceProcess implements NoticeService{

	@Autowired
	NoticeEntityRepository noticeRepo;
	
	@Override
	public void save(NoticeDTO noticeDTO) {
		noticeRepo.save(noticeDTO.toEntity());
	}

	@Override
	public void noticeList(Model model) {
		model.addAttribute("nList", noticeRepo.findAll()
				.stream()
				.map(NoticeEntity::toDTO)
				.collect(Collectors.toList())
				);
	}

	@Override
	public void noticeDetail(long no, Model model) {
		model.addAttribute("DetailList", noticeRepo.findById(no)
				.stream()
				.map(NoticeEntity::toDTO)
				.collect(Collectors.toList())
				);
	}

}
