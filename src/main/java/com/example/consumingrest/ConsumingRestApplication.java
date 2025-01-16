package com.example.consumingrest;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.Spring;

import org.slf4j.Logger;


@RestController
@SpringBootApplication
public class ConsumingRestApplication {

    private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(ConsumingRestApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/check")
    public Quote call(RestTemplate restTemplate) throws Exception {
        Quote quote = restTemplate.getForObject(
					"http://localhost:8080/api/random", Quote.class);
        log.info("called : " + quote.toString());
        return quote;
    }
    

    

    //the run was called when main runned
	@Bean
	@Profile("!test")
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        // CommandLineRunner is a functional interface in Spring Boot used to execute code when the application starts. It is typically implemented to run initialization logic or perform tasks that need to be executed during the startup phase of a Spring Boot application.
        // if error, the api/random is failed to run, or failed to call, or not run
        // the quoter can use this one repo https://github.com/spring-guides/quoters
		// ref https://spring.io/guides
		return args -> {
			Quote quote = restTemplate.getForObject(
					"http://localhost:8080/api/random", Quote.class);
			log.info(quote.toString());
            log.info("was started");
		};
	}
}