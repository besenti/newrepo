package com.assignment.shortestpath;

import com.assignment.shortestpath.service.FileInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "com.assignment.shortestpath.entity")
public class ShortestpathApplication {

	@Autowired
	FileInputService fileInputService;

	public static void main(String[] args) {
		SpringApplication.run(ShortestpathApplication.class, args);
	}

	@PostConstruct
	private void init(){
		fileInputService.init();
	}
}
