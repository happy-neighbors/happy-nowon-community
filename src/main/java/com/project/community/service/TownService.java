package com.project.community.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.project.community.domain.dto.TownDTO;

public interface TownService {

	void townSave(TownDTO dto, Authentication user);

	void townList(Model model);

	void detailTown(Long pk, Model model);
	
}
