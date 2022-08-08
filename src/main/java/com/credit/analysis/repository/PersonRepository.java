package com.credit.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.analysis.model.Person;

public interface PersonRepository  extends JpaRepository<Person, Long> {

}
