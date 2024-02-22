package com.project.community.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeEntityRepository extends JpaRepository<NoticeEntity, Long>{

	List<NoticeEntity> findByTitle(String keyword);

}
