package com.example.springboot;

import java.net.http.HttpHeaders;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
	private static final Logger logger = LogManager.getLogger(HelloController.class);

	@Value("${URL}")
	String serviceburl;

	@GetMapping("/")
	public String index() {
		logger.info("Greetings from Spring Boot Service A!");
		return "Greetings from Spring Boot Service A!";
	}

	@GetMapping("/users")
	public String users() {
		logger.info("Greetings User!");
		return "Greetings User!";
	}

	@GetMapping("/admins")
	public String admins() {
		logger.info("Greetings Admin!");
		return "Greetings Admin!";
	}

	@GetMapping("/hitB")
	public String hitB() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(serviceburl + "/", String.class);
		return response.getBody();

	}

	@GetMapping("/health")
	Map<String, Object> echo(HttpServletRequest request,
			@RequestHeader HttpHeaders headers,
			@RequestBody(required = false) Map<String, Object> body) {

		Instant now = Instant.now();
		Cookie[] cookies = request.getCookies();

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("clientIpAddress", request.getRemoteAddr());
		result.put("cookies", cookies == null ? new ArrayList()
				: Arrays.stream(cookies).toList());
		result.put("headers", headers.allValues(serviceburl)); // simplify json response
		result.put("httpVersion", request.getProtocol());
		result.put("method", request.getMethod());
		result.put("body", body);
		result.put("queryString", request.getQueryString());
		result.put("startedDateTime", now);
		result.put("url", request.getRequestURL());

		return result;
	}

}
