package com.demo.microservicesdemo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;

import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.microservicesdemo.APIVersioning.VersioningPersonController;
import com.demo.microservicesdemo.entity.User;
import com.demo.microservicesdemo.exception.UserNotFoundException;
import com.demo.microservicesdemo.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	private UserService service;

	public UserController() {
	}

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return service.finaAll();
	}

	// HATEOAS - Hypermedia as the Engine of Application State -- see data and
	// perform actions(using links)
	// Implemetation option - 1. Custom format and implementation
	// 2. Use Standard implementation - HAL(JSON Hypertext Application Language)
	// Simple format that gives a consistent and easy way to hyperlink between
	// resources in your API - using EntityModel and WebMvcLinkBuilder
	@GetMapping("/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		User user = service.findOne(id);

		if (user == null)
			throw new UserNotFoundException("id : " + id);

		EntityModel<User> entityModel = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getUsers());
		entityModel.add(link.withRel("all-users"));
		
//		WebMvcLinkBuilder link = linkTo(methodOn(VersioningPersonController.class).getSecondVersionOfPerson());
//		entityModel.add(link.withRel("Second version of Person"));
		
		return entityModel;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteUserById(id);
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.addUser(user);

		// /users/4 => /users/{id} , user.getId
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
