package com.project.community.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.community.domain.entity.EmployeeEntity;
import com.project.community.domain.entity.EmployeeEntityRepository;

public class EmployeeDetailsService implements UserDetailsService {
	
	@Autowired
	private EmployeeEntityRepository employeeEntityRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		EmployeeEntity member = employeeEntityRepository.findByEmpUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
		Set<SimpleGrantedAuthority> grantedAuthority = member.getMyRoles().stream()
				.map(myRole -> new SimpleGrantedAuthority(myRole.name())).collect(Collectors.toSet());
		
		return new EmployeeDetails(member, member.getEmpName(), grantedAuthority);
	}
}