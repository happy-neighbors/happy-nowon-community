package com.project.community.domain.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnabadaEntityRepository extends JpaRepository<AnabadaEntity, Long>{

	List<AnabadaEntity> findByEmployee(EmployeeEntity en);

	List<AnabadaEntity> findByEmployee_empNo(long empNo);

	List<AnabadaEntity> findTop5ByOrderByNoDesc();
	
}
