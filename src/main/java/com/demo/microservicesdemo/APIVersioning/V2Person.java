package com.demo.microservicesdemo.APIVersioning;

public class V2Person {
	private Name name;

	public V2Person(Name name) {
		super();
		this.name = name;
	}

	public Name getName() {
		return name;
	}

	@Override
	public String toString() {
		return "V2Person [name=" + name + "]";
	}

}
