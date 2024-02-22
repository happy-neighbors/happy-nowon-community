package com.project.community.service;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.project.community.domain.dto.NoticeDTO;

public interface NoticeService {

	void save(NoticeDTO noticeDTO, Authentication user);

	void noticeList(Model model);

	void noticeDetail(long no, Model model);

	void noticeEdit(long no, String title, String content, boolean type, LocalDateTime end);

	void noticeDelete(long no);

}
