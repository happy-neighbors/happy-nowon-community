package com.project.community.service.impl;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.community.domain.dto.AnabadaDTO;
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.PageResultDTO;
import com.project.community.domain.entity.AnabadaEntity;
import com.project.community.domain.entity.AnabadaEntityRepository;
import com.project.community.domain.entity.EmployeeEntityRepository;
import com.project.community.security.EmployeeDetails;
import com.project.community.service.AnabadaService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnabadaServiceProcess implements AnabadaService{

	@Autowired
	AnabadaEntityRepository anabadaRepo;
	@Autowired
	EmployeeEntityRepository empRepo;
	
	
	
	@Override
	public void anabadaSave(AnabadaDTO dto, Authentication user) {
		if(user!=null) {
			System.out.println(">>>"+user.getPrincipal());
			EmployeeDetails emp=(EmployeeDetails) user.getPrincipal();
			anabadaRepo.save(dto.toEntity(empRepo.findByEmpNo(emp.getEmpNo())));
		}
	}

	@Override
	public void anabadaList(Model model) {
		Sort sort=Sort.by(Direction.DESC, "no");
		model.addAttribute("list", anabadaRepo.findAll(sort).stream()
				.map(AnabadaEntity::toAnabadaDTO)
				.collect(Collectors.toList())
				);
		
	}
	@Override
	public PageResultDTO<AnabadaDTO, AnabadaEntity> getList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = pageRequestDTO.getPageable(10 ,Sort.by("no").descending());

		Page<AnabadaEntity> result = anabadaRepo.findAll(pageable);
		
		Function<AnabadaEntity, AnabadaDTO> fn = entity -> entity.toAnabadaDTO();
		
		return new PageResultDTO<>(result, fn);
	}

	@Override
	public void detailAnabada(Long pk, Model model) {
		model.addAttribute("dto", anabadaRepo.findById(pk)
				.map(AnabadaEntity::toAnabadaDTO)
				.orElseThrow());
		
	}

	@Override
	public void deleteAnabada(long pk) {
		anabadaRepo.deleteById(pk);
		
	}

	@Transactional
	@Override
	public void updateAnabada(long pk, String area, String content, String title, String state) {
		anabadaRepo.findById(pk).orElseThrow()
				.update(area, content, title, state);
		
	}

	
	
}
