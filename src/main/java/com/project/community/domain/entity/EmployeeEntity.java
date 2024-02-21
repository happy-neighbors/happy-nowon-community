package com.project.community.domain.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import com.project.community.security.EmployeeRole;
import com.project.community.security.MyRole;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@SequenceGenerator(name = "gen_emp_seq", sequenceName = "emp_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeEntity {
	
	@Id
	@GeneratedValue(generator = "gen_emp_seq", strategy = GenerationType.SEQUENCE)
	private long empNo;
	
	@Column(nullable = false, unique = true)
	private String empUsername;
	
	@Column(nullable = false)
	private String empPassword;
	
	@Column(nullable = false)
	private String empName;
	
	
	private String empRole;
	
	private String provider;
	private String providerId;
	
	@CollectionTable(name = "employeeRole")
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Set<MyRole> myRoles = new HashSet<>();
	
	public EmployeeEntity addRole(MyRole myRole) {
		myRoles.add(myRole);
		return this;
	}
	
	


	
}