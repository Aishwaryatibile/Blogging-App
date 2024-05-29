package com.demo.microservicesdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.microservicesdemo.entity.User;

@Service
public interface UserService {

	public List<User> finaAll();

	public User findOne(int id);
	
	public void deleteUserById(int id);
	
	public User addUser(User user);
}
