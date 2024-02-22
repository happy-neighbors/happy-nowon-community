package com.project.community.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.PageResultDTO;
import com.project.community.domain.dto.TownDTO;
import com.project.community.domain.entity.TownEntity;

public interface TownService {

	void townSave(TownDTO dto, Authentication user);

	void townList(Model model);

	void detailTown(Long pk, Model model);
	
	void deleteTown(long pk);

	void updateTown(long pk, String area, String content, String title);

	PageResultDTO<TownDTO, TownEntity> getList(PageRequestDTO pageRequestDTO);

	List<TownEntity> getTop5Procedures();
	
}
