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

	@GetMapping("/health")
	Map<String, Object> echo(HttpServletRequest request,
		     @RequestHeader HttpHeaders headers,
		     @RequestBody(required = false) Map<String, Object> body) {

		Instant now = Instant.now();
		Cookie[] cookies = request.getCookies();

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("clientIpAddress", request.getRemoteAddr());
		result.put("cookies", cookies == null ? emptyList()
						      : Arrays.stream(cookies).toList());
		result.put("headers", headers.toSingleValueMap()); // simplify json response
		result.put("httpVersion", request.getProtocol());
		result.put("method", request.getMethod());
		result.put("body", body);
		result.put("queryString", request.getQueryString());
		result.put("startedDateTime", now);
		result.put("url", request.getRequestURL());

		return result;
	}

}
