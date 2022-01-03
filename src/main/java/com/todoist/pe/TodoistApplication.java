package com.todoist.pe;

import com.todoist.pe.config.ServerSentEventConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableReactiveMongoAuditing
@EnableMongoRepositories
@EnableConfigurationProperties({ServerSentEventConfig.class})
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation APIs v1.0"))
public class TodoistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoistApplication.class, args);
	}

}
