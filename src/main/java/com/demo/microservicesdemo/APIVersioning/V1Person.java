package com.demo.microservicesdemo.APIVersioning;

public class V1Person {
	private String name;

	public V1Person(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "V1Person [name=" + name + "]";
	}

}
