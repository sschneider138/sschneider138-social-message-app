package com.backend.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EmailServiceApplication {

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure()
        .directory(".")
        .ignoreIfMalformed()
        .ignoreIfMissing()
        .load();

    dotenv.entries().forEach(entry -> System.out.println(entry.getKey() + " = " + entry.getValue()));
    SpringApplication.run(EmailServiceApplication.class, args);
  }

}
