package com.alareubang.koala.config.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String id);
	
    User findByUsername(String username);
}