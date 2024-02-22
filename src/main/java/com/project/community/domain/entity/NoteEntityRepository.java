package com.project.community.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.community.domain.entity.NoteEntity.NoteEntityBuilder;

public interface NoteEntityRepository extends JpaRepository<NoteEntity, Long>{

	void save(NoteEntityBuilder entity);
	
}
