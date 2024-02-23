package com.project.community.service.impl;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.community.domain.dto.NoteDTO;
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.PageResultDTO;
import com.project.community.domain.dto.TownDTO;
import com.project.community.domain.entity.NoteEntity;
import com.project.community.domain.entity.NoteEntityRepository;
import com.project.community.domain.entity.TownEntity;
import com.project.community.service.NoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoteServiceProcess implements NoteService{
	
	@Autowired
	NoteEntityRepository noteRepo;

	@Override
	public void noteSave(NoteDTO dto) {
		noteRepo.save(dto.toEntity());
		
	}

	@Override
	public void sendNoteList(Model model) {
		Sort sort=Sort.by(Direction.DESC, "no");
		model.addAttribute("list", noteRepo.findAll(sort).stream()
				.map(NoteEntity::toNoteDTO)
				.collect(Collectors.toList())
				);
		
	}

	@Override
	public PageResultDTO<NoteDTO, NoteEntity> getList(PageRequestDTO pageRequestDTO) {
		Pageable pageable = pageRequestDTO.getPageable(10 ,Sort.by("no").descending());

		Page<NoteEntity> result = noteRepo.findAll(pageable);
		
		Function<NoteEntity, NoteDTO> fn = entity -> entity.toNoteDTO();
		
		return new PageResultDTO<>(result, fn);
	}

	@Override
	public void takeNoteList(Model model) {
		Sort sort=Sort.by(Direction.DESC, "no");
		model.addAttribute("list", noteRepo.findAll(sort).stream()
				.map(NoteEntity::toNoteDTO)
				.collect(Collectors.toList())
				);
		
	}

	@Override
	public NoteEntity getState(long pk) {
		Optional<NoteEntity> note=this.noteRepo.findById(pk);
		if(note.isPresent()) {
			NoteEntity note1=note.get();
			note1.setState(true);
			this.noteRepo.save(note1);
			return note1;
		} else {
			throw new DataSourceException("question not found");
		}
		
	}

	@Override
	public void detailNote(long pk, Model model) {
		model.addAttribute("dto", noteRepo.findById(pk)
				.map(NoteEntity::toNoteDTO)
				.orElseThrow());
		
	}

	@Override
	public Long countState() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return noteRepo.countState(username);
	}

}
