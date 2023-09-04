package com.springbatch.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbatch.example.model.Data;

public interface UserRepository extends JpaRepository<Data,Integer> {
	

}
