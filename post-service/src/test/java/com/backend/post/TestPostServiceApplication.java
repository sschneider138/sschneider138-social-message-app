package com.backend.post;

import org.springframework.boot.SpringApplication;

public class TestPostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(PostServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
