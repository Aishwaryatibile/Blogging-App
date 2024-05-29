package com.demo.microservicesdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.microservicesdemo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
