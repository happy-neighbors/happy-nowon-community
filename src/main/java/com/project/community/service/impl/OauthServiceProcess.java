package com.project.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.community.domain.dto.OauthDTO;
import com.project.community.domain.entity.EmployeeEntityRepository;
import com.project.community.security.MyRole;
import com.project.community.service.OauthService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OauthServiceProcess implements OauthService {

	@Autowired
	EmployeeEntityRepository memRepo;


	@Override
	@Transactional
	public void save(OauthDTO oauthDTO) {
		memRepo.save(oauthDTO.getDTO().addRole(MyRole.USER));
	}


}