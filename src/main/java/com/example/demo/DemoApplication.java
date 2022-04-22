package com.example.demo;

import com.example.demo.project.api.RestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	private static String answer;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		RestTemplateBuilder builder = new RestTemplateBuilder();
		RestService service = new RestService(builder);
		answer = service.getPostsPlainJSON().substring(11,56) + "}";

	}

	@GetMapping
	public String hello(){
		return "Hello from CGI project, Adeljushka. Here is the answer: " + answer;
	}



}
