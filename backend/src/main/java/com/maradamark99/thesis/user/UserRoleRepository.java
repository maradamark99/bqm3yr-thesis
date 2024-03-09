package com.maradamark99.thesis.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	UserRole findByRoleIgnoreCase(String role);
	
	boolean existsByRole(String role);
	
}
