package com.project.community.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.project.community.domain.entity.EmployeeEntity;

import lombok.Getter;

@Getter
public class EmployeeDetails extends User {

	private static final long serialVersionUID = 1L;
	
	private long empNo;
	private String empName;
	private String empRole;
	
	public EmployeeDetails(EmployeeEntity member, String password, Collection<? extends GrantedAuthority> authorities) {
		super(member.getEmpUsername(), password, authorities);
		this.empName=member.getEmpName();
	}
}