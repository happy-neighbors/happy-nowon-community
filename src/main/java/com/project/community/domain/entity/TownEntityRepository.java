package com.project.community.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TownEntityRepository extends JpaRepository<TownEntity, Long>{

	List<TownEntity> findByEmployee(EmployeeEntity en);

	List<TownEntity> findByEmployee_empNo(long empNo);
	
}
