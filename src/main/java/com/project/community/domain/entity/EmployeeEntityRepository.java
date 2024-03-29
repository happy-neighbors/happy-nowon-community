package com.project.community.domain.entity;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeEntityRepository extends JpaRepository<EmployeeEntity, Long> {
	
	Optional<EmployeeEntity> findByEmpUsername(String empUsername);

	boolean existsByEmpUsername(String empUsername); //USername이 이미 존재하는지.
	
	EmployeeEntity findByEmpName(String empName);
	
    boolean existsByEmpName(String empName);

    EmployeeEntity findByEmpNo(long empNo);
    
    List<EmployeeEntity> findAllByEmpName(String empName);

	List<EmployeeEntity> findAllByEmpRole(String empRole);


	
	


}