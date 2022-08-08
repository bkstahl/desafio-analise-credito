package com.credit.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.analysis.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByLogin(String login);
}
