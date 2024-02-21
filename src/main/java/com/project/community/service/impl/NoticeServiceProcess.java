package com.project.community.service.impl;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.community.domain.dto.NoticeDTO;
import com.project.community.domain.entity.EmployeeEntityRepository;
import com.project.community.domain.entity.NoticeEntity;
import com.project.community.domain.entity.NoticeEntityRepository;
import com.project.community.security.EmployeeDetails;
import com.project.community.service.NoticeService;

@Service
public class NoticeServiceProcess implements NoticeService{

	@Autowired
	NoticeEntityRepository noticeRepo;
	
	@Autowired
	EmployeeEntityRepository empRepo;
	
	@Override
	public void save(NoticeDTO noticeDTO, Authentication user) {
		EmployeeDetails empNo = (EmployeeDetails) user.getPrincipal();
		noticeRepo.save(noticeDTO.toEntity(empRepo.findByEmpNo(empNo.getEmpNo())));
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
				.map(NoticeEntity::toDTO)
				.orElseThrow()
				);
	}

	@Override
	public void noticeEdit(long no, String title, String content, boolean type, LocalDateTime end) {
		NoticeEntity nEntity = noticeRepo.findById(no).orElseThrow();
		
		if(nEntity!=null) {
			nEntity.setTitle(title);
			nEntity.setContent(content);
			nEntity.setType(type);
			nEntity.setEnd(end);
			
			noticeRepo.save(nEntity);
		}
		
	}

	@Override
	public void noticeDelete(long no) {
		NoticeEntity nEntity = noticeRepo.findById(no).orElseThrow();
		noticeRepo.delete(nEntity);
	}

}
