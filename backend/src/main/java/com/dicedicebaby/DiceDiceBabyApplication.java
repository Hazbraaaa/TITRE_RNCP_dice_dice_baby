package com.dicedicebaby;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DiceDiceBabyApplication {

	@PersistenceContext
	private EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(DiceDiceBabyApplication.class, args);
	}

	@GetMapping("/test-db")
	public String testDb() {
		try {
			String now = (String) entityManager
					.createNativeQuery("SELECT NOW()")
					.getSingleResult()
					.toString();
			return "DB connected! Current time: " + now;
		} catch (Exception e) {
			return "DB connection failed: " + e.getMessage();
		}
	}
}
