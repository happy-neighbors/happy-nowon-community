package com.project.community.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeEntityRepository extends JpaRepository<NoticeEntity, Long>{

	@Query("SELECT n FROM NoticeEntity n WHERE n.title LIKE %:keyword%")
	List<NoticeEntity> findByTitleContaining(@Param("keyword") String keyword);

}
