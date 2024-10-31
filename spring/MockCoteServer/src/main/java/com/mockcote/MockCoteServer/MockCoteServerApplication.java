package com.mockcote.MockCoteServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MockCoteServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockCoteServerApplication.class, args);
	}

}
