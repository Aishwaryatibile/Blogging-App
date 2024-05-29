package com.demo.microservicesdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.microservicesdemo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
