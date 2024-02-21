package com.project.community.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.community.domain.dto.TownDTO;
import com.project.community.domain.entity.EmployeeEntityRepository;
import com.project.community.domain.entity.TownEntity;
import com.project.community.domain.entity.TownEntityRepository;
import com.project.community.security.EmployeeDetails;
import com.project.community.service.TownService;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TownServiceProcess implements TownService{

	@Autowired
	TownEntityRepository townRepo;
	@Autowired
	EmployeeEntityRepository empRepo;
	
	
	
	@Override
	public void townSave(TownDTO dto, Authentication user) {
		if(user!=null) {
			System.out.println(">>>"+user.getPrincipal());
			//MyOAuthUser emp=(MyOAuthUser) user.getPrincipal();
			
			//townRepo.save(dto.toEntity(emp.getMemberEntity().getEmpNo()));
		}
	}

	@Override
	public void townList(Model model) {
		Sort sort=Sort.by(Direction.DESC, "no");
		model.addAttribute("list", townRepo.findAll(sort).stream()
				.map(TownEntity::toTownDTO)
				.collect(Collectors.toList())
				);
		
	}

	@Override
	public void detailTown(Long pk, Model model) {
		model.addAttribute("dto", townRepo.findById(pk)
				.map(TownEntity::toTownDTO)
				.orElseThrow());
		
	}
	
}
