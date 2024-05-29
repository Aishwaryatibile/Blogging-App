package com.demo.microservicesdemo.APIVersioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

	// http://localhost:8080/v1/person -- URI versioning
	@GetMapping("/v1/person")
	public V1Person getFirstVersionOfPerson() {
		return new V1Person("Bob Charlie");
	}

	// http://localhost:8080/v2/person -- URI versioning
	@GetMapping("/v2/person")
	public V2Person getSecondVersionOfPerson() {
		return new V2Person(new Name("Bob", "Charlie"));
	}

	// http://localhost:8080/person?version1 -- Request parameter versioning
	@GetMapping(path="/person", params="version1")
	public V1Person getFirstVersionOfPersonRequestParameter() {
		return new V1Person("Bob Charlie");
	}

	// http://localhost:8080/person?version2 -- Request parameter versioning
	@GetMapping(path="person", params="version2")
	public V2Person getSecondVersionOfPersonRequestParameter() {
		return new V2Person(new Name("Bob", "Charlie"));
	}
	
	//(Custom) headers versioning
	//url-/person/header headers=[X-API-VERSION1]
	@GetMapping(path="/person/header", headers = "X-API-VERSION=1")
	public V1Person getFirstVersionOfPersonRequestHeader() {
		return new V1Person("Bob Charlie");
	}
	
	//url-/person/header headers=[X-API-VERSION1]
	@GetMapping(path="/person/header", headers = "X-API-VERSION=2")
	public V2Person getSecondVersionOfPersonRequestHeader() {   
		return new V2Person(new Name("Bob", "Charlie"));
	}
	
	
	
	
}
