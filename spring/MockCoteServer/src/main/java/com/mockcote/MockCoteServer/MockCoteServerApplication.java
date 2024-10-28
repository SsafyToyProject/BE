package com.mockcote.MockCoteServer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mockcote.MockCoteServer.model.mapper")
public class MockCoteServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockCoteServerApplication.class, args);
	}

}
