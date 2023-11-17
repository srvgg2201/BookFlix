package com.project.bookflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookFlixApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookFlixApplication.class, args);
	}

}
