package com.mockcote.MockCoteServer.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//Swagger-UI 확인
//http://localhost:8080/swagger-ui/index.html

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		System.out.println("openAPI-------------");
		Info info = new Info().title("MockCote API 명세서").description(
				"<h3>MockCote API Reference for Developers</h3>MockCote API<br>")
				.version("v1").contact(new io.swagger.v3.oas.models.info.Contact().name("hissam")
						.email("hissam@ssafy.com").url("http://edu.ssafy.com"));

		return new OpenAPI().components(new Components()).info(info);
	}
	
	@Bean
	public GroupedOpenApi studyApi() {
		return GroupedOpenApi.builder().group("mockcote").pathsToMatch("/study/**","/user/**","/session/**", "/tracker/**","/crawl/**").build();
	}

//	@Bean
//	public GroupedOpenApi userApi() {
//		return GroupedOpenApi.builder().group("ssafy-user").pathsToMatch("/user/**").build();
//	}
//	
//	@Bean
//	public GroupedOpenApi sessionApi() {
//		return GroupedOpenApi.builder().group("ssafy-session").pathsToMatch("/session/**").build();
//	}
//	
//	@Bean
//	public GroupedOpenApi trackerApi() {
//		return GroupedOpenApi.builder().group("ssafy-tracker").pathsToMatch("/tracker/**").build();
//	}
//	
//	@Bean
//	public GroupedOpenApi crawlApi() {
//		return GroupedOpenApi.builder().group("ssafy-crawl").pathsToMatch("/crawl/**").build();
//	}

}