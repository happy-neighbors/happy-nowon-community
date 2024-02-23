package com.project.community.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.community.domain.dto.NoticeDTO;
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.PageResultDTO;
import com.project.community.domain.entity.EmployeeEntityRepository;
import com.project.community.domain.entity.NoticeEntity;
import com.project.community.domain.entity.NoticeEntityRepository;
import com.project.community.security.EmployeeDetails;
import com.project.community.service.NoticeService;

import jakarta.transaction.Transactional;

@Service
public class NoticeServiceProcess implements NoticeService{

	@Autowired
	NoticeEntityRepository noticeRepo;
	
	@Autowired
	EmployeeEntityRepository empRepo;
	
	//저장
	@Override
	public void save(NoticeDTO noticeDTO, Authentication user) {
		EmployeeDetails empNo = (EmployeeDetails) user.getPrincipal();
		noticeRepo.save(noticeDTO.toEntity(empRepo.findByEmpNo(empNo.getEmpNo())));
	}

	//리스트 불러오기
	@Override
	public void noticeList(Model model) {
		model.addAttribute("nList", noticeRepo.findAll()
				.stream()
				.map(NoticeEntity::toDTO)
				.collect(Collectors.toList())
				);
	}

	//게시글 상세페이지
	@Override
	public void noticeDetail(long no, Model model) {
		model.addAttribute("DetailList", noticeRepo.findById(no)
				.map(NoticeEntity::toDTO)
				.orElseThrow()
				);
	}

	//수정
	@Override
	public void noticeEdit(long no, String title, String content, boolean type, LocalDateTime end) {
		NoticeEntity nEntity = noticeRepo.findById(no).orElseThrow();
		
		if(nEntity!=null) {
			nEntity.setTitle(title);
			nEntity.setContent(content);
			nEntity.setType(type);
			nEntity.setEnd(end);
			
			noticeRepo.save(nEntity);
		}
		
	}

	//삭제
	@Override
	public void noticeDelete(long no) {
		NoticeEntity nEntity = noticeRepo.findById(no).orElseThrow();
		noticeRepo.delete(nEntity);
	}

	//페이징처리
	@Override
	public PageResultDTO<NoticeDTO, NoticeEntity> getlist(PageRequestDTO prDTO) {
		Pageable pageAble = prDTO.getPageable(10,Sort.by("no").descending());
		Page<NoticeEntity> result = noticeRepo.findAll(pageAble);
		Function<NoticeEntity, NoticeDTO> fn = entity -> entity.toDTO();
		return new PageResultDTO<>(result, fn);
	}
	
	//조회수
	@Override
	public NoticeEntity getNotice(long no) {
		Optional<NoticeEntity> notice = this.noticeRepo.findById(no);
		if(notice.isPresent()) {
			NoticeEntity nEntity = notice.get();
			nEntity.setReadCount(nEntity.getReadCount()+1);
			this.noticeRepo.save(nEntity);
			return nEntity;
		}else {
			throw new DataSourceException("question not found");
		}
		
	}

	//검색
	@Transactional
	public List<NoticeDTO> search(String keyword) {
		List<NoticeEntity> nEntity = noticeRepo.findByTitleContaining(keyword);
		List<NoticeDTO> nDTO = new ArrayList<>();
		
		if(nEntity.isEmpty()) return nDTO;
		for(NoticeEntity noticeEntity : nEntity) {
			nDTO.add(this.searchToDTO(noticeEntity));
		}
		return nDTO;
	}

	private NoticeDTO searchToDTO(NoticeEntity noticeEntity) {
		return NoticeDTO.builder()
				.no(noticeEntity.getNo())
				.title(noticeEntity.getTitle())
				.content(noticeEntity.getContent())
				.end(noticeEntity.getEnd())
				.type(noticeEntity.isType())
				.start(noticeEntity.getStart())
				.readCount(noticeEntity.getReadCount())
				.build();
	}

	@Override
	public List<NoticeEntity> getTop5Procedures() {
		return noticeRepo.findTop5ByOrderByNoDesc();
	}



}
