package com.project.community.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.PageResultDTO;
import com.project.community.domain.dto.TownCommentDTO;
import com.project.community.domain.dto.TownDTO;
import com.project.community.domain.entity.EmployeeEntityRepository;
import com.project.community.domain.entity.TownEntity;
import com.project.community.domain.entity.TownEntityRepository;
import com.project.community.security.EmployeeDetails;
import com.project.community.service.TownService;

import jakarta.transaction.Transactional;
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
			EmployeeDetails emp=(EmployeeDetails) user.getPrincipal();
			
			townRepo.save(dto.toEntity(empRepo.findByEmpNo(emp.getEmpNo())));
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
	public PageResultDTO<TownDTO, TownEntity> getList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = pageRequestDTO.getPageable(10 ,Sort.by("no").descending());

		Page<TownEntity> result = townRepo.findAll(pageable);
		
		Function<TownEntity, TownDTO> fn = entity -> entity.toTownDTO();
		
		return new PageResultDTO<>(result, fn);
	}

	@Override
	public void detailTown(Long pk, Model model) {
		model.addAttribute("dto", townRepo.findById(pk)
				.map(TownEntity::toTownDTO)
				.orElseThrow());
		
	}

	@Override
	public void deleteTown(long pk) {
		townRepo.deleteById(pk);
		
	}

	@Transactional
	@Override
	public void updateTown(long pk, String area, String content, String title) {
		townRepo.findById(pk).orElseThrow()
				.update(area, content, title);
		
	}

	@Override
	public List<TownEntity> getTop5Procedures() {
		return townRepo.findTop5ByOrderByNoDesc();
	}

	@Override
	public TownEntity getCount(long pk) {
		Optional<TownEntity> town=this.townRepo.findById(pk);
		if(town.isPresent()) {
			TownEntity town1 = town.get();
			town1.setReadCount(town1.getReadCount()+1);
			this.townRepo.save(town1);
			return town1;
		}else {
			throw new DataSourceException("question not found");
		}
		
		
	}

	

	
	
}
