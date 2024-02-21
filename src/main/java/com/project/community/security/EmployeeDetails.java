package com.project.community.security;

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
	
	public EmployeeDetails(String empUsername, String empPassword, Collection<? extends GrantedAuthority> authorities) {
		super(empUsername, empPassword, authorities);
	}
	
	public EmployeeDetails(EmployeeEntity member, String empPassword, Collection<? extends GrantedAuthority> authorities) {
		super(member.getEmpUsername(), empPassword, authorities);
		this.empName=member.getEmpName();
	}
	
	public EmployeeDetails(EmployeeEntity employeeEntity) {
		this(employeeEntity.getEmpUsername(), employeeEntity.getEmpPassword(), employeeEntity.getMyRoles().stream()
				.map(EmployeeRole -> new SimpleGrantedAuthority(EmployeeRole.name()))
				.collect(Collectors.toSet()));

		this.empNo = employeeEntity.getEmpNo();
		this.empName = employeeEntity.getEmpName();
		this.empRole = employeeEntity.getEmpRole();
	}
	
}