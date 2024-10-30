package com.mockcote.MockCoteServer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({
    "com.mockcote.MockCoteServer.domain.crawl.model.mapper",
    "com.mockcote.MockCoteServer.domain.tracker.model.mapper",
    "com.mockcote.MockCoteServer.domain.user.model.mapper",
    "com.mockcote.MockCoteServer.domain.session.model.mapper",
    "com.mockcote.MockCoteServer.domain.study.model.mapper"
})
@EnableScheduling
public class MockCoteServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockCoteServerApplication.class, args);
	}

}
