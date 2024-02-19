package com.project.community.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeRole {
	
	SUPERVISOR("관리자"),
	MANAGER("팀장"),
	STAFF("직원"),
	CLERK("사원");
	
	private final String roleName;
}