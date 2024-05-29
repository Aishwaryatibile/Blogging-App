package com.demo.microservicesdemo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import com.demo.microservicesdemo.entity.Post;
import com.demo.microservicesdemo.entity.User;
import com.demo.microservicesdemo.exception.UserNotFoundException;
import com.demo.microservicesdemo.repository.PostRepository;
import com.demo.microservicesdemo.repository.UserRepository;
import com.demo.microservicesdemo.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserJPAController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	public UserJPAController(UserRepository userRepository,PostRepository postRepository ) {
		this.userRepository = userRepository;
		this.postRepository=postRepository;
	}

	public UserJPAController() {
	}

	@GetMapping("/jpa/users")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/posts")
	public List<Post> getPosts() {
		return postRepository.findAll();
	}

	// HATEOAS - Hypermedia as the Engine of Application State -- see data and
	// perform actions(using links)
	// Implemetation option - 1. Custom format and implementation
	// 2. Use Standard implementation - HAL(JSON Hypertext Application Language)
	// Simple format that gives a consistent and easy way to hyperlink between
	// resources in your API - using EntityModel and WebMvcLinkBuilder
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("id : " + id+" not found");

		EntityModel<User> entityModel = EntityModel.of(user.get());

		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrivePostsForUser(id));
		entityModel.add(link.withRel("all-posts of "+user.get().getName()));

//		WebMvcLinkBuilder link = linkTo(methodOn(VersioningPersonController.class).getSecondVersionOfPerson());
//		entityModel.add(link.withRel("Second version of Person"));

		return entityModel;
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);

		if (user.getName() == null || user.getBirthDate() == null)
			throw new UserNotFoundException("Please enter correct information");

		// /users/4 => /users/{id} , user.getId
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrivePostsForUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("Id :" + id);

		return user.get().getPosts();
	}
	
	@GetMapping("/jpa/users/{id}/posts/{postId}")
	public Post retrivePostForUser(@PathVariable int id,@PathVariable int postId ) throws Exception {
		Optional<User> user = userRepository.findById(id);
		Optional<Post> postId1 = postRepository.findById(postId);

		if (user.isEmpty())
			throw new UserNotFoundException("Id :" + id);
		
		if(postId1.isEmpty())
			throw new Exception("Incorrect post ID, please enter correct post ID");

		return postId1.get();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostsForUser(@PathVariable int id,@Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("Id :" + id);
		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedPost .getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
	

}
