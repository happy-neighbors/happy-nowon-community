package com.project.community.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.project.community.domain.dto.NoteDTO;
import com.project.community.domain.dto.PageRequestDTO;
import com.project.community.domain.dto.PageResultDTO;
import com.project.community.domain.entity.NoteEntity;

public interface NoteService {

	void noteSave(NoteDTO dto);

	void sendNoteList(Model model);

	PageResultDTO<NoteDTO, NoteEntity> getList(PageRequestDTO pageRequestDTO);

	void takeNoteList(Model model);

	NoteEntity getState(long pk);

	void detailNote(long pk, Model model);
	
	Long countState();

}
