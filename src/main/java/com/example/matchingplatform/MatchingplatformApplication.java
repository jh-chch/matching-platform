package com.example.matchingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MatchingplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchingplatformApplication.class, args);
	}

}
