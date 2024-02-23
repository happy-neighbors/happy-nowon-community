package com.project.community.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.community.domain.entity.NoteEntity.NoteEntityBuilder;

public interface NoteEntityRepository extends JpaRepository<NoteEntity, Long>{

	void save(NoteEntityBuilder entity);
	
	 @Query("SELECT COUNT(e) FROM NoteEntity e WHERE e.reader = :username AND e.state = false")
	Long countState(@Param(value = "username")String username);
	
}
