package com.project.community.service;

import org.springframework.ui.Model;

import com.project.community.domain.dto.NoticeDTO;

public interface NoticeService {

	void save(NoticeDTO noticeDTO);

	void noticeList(Model model);

}
