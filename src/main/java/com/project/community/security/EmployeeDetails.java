package com.project.community.security;

import java.math.BigInteger;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.project.community.domain.entity.EmployeeEntity;

import lombok.Getter;

@Getter
public class EmployeeDetails extends User {

	private static final long serialVersionUID = 1L;
	
	private long empNo;
	private String empName;
	private String empRole;
	private String empPhone;
	
	public EmployeeDetails(String empUsername, String empPassword, String empPhone, Collection<? extends GrantedAuthority> authorities) {
		super(empUsername, empPassword, authorities);
	}

	public EmployeeDetails(EmployeeEntity employeeEntity) {
		this(employeeEntity.getEmpUsername(), employeeEntity.getEmpPassword(), employeeEntity.getEmpPhone(), employeeEntity.getMyRoles().stream()
				.map(EmployeeRole -> new SimpleGrantedAuthority(EmployeeRole.name()))
				.collect(Collectors.toSet()));

		this.empNo = employeeEntity.getEmpNo();
		this.empName = employeeEntity.getEmpName();
		this.empRole = employeeEntity.getEmpRole();
		this.empPhone= employeeEntity.getEmpPhone();
	}

}