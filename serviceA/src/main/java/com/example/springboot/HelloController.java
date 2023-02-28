package com.example.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
	@Value("${URL}")
	String serviceburl;

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot Service A!";
	}

	@GetMapping("/hitB")
	public String hitB(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response  = restTemplate.getForEntity(serviceburl + "/", String.class);
		return response.getBody();
	}

}
