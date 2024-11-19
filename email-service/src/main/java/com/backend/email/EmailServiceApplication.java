package com.backend.email;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServiceApplication {

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure()
        .directory("./src")
        .ignoreIfMalformed()
        .ignoreIfMissing()
        .load();

    dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    SpringApplication.run(EmailServiceApplication.class, args);
  }

}
