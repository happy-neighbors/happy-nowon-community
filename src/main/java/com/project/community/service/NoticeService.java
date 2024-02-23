package com.project.community.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.project.community.domain.dto.NoticeDTO;
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.PageResultDTO;
import com.project.community.domain.entity.NoticeEntity;

public interface NoticeService {

	void save(NoticeDTO noticeDTO, Authentication user);

	void noticeList(Model model);

	void noticeDetail(long no, Model model);

	void noticeEdit(long no, String title, String content, boolean type, LocalDateTime end);

	void noticeDelete(long no);

	PageResultDTO<NoticeDTO, NoticeEntity> getlist(PageRequestDTO prDTO);

	NoticeEntity getNotice(long no);

	List<NoticeDTO> search(String keyword);


}
