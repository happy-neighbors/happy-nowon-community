package com.project.community.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.project.community.domain.dto.AnabadaDTO;
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.PageResultDTO;
import com.project.community.domain.entity.AnabadaEntity;

public interface AnabadaService {

	void anabadaSave(AnabadaDTO dto, Authentication user);

	void anabadaList(Model model);

	void detailAnabada(Long pk, Model model);
	
	void deleteAnabada(long pk);

	void updateAnabada(long pk, String area, String content, String title, String state);

	PageResultDTO<AnabadaDTO, AnabadaEntity> getList(PageRequestDTO pageRequestDTO);
	
}
