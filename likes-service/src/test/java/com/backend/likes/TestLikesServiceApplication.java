package com.backend.likes;

import org.springframework.boot.SpringApplication;

public class TestLikesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(LikesServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
