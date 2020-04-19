package com.proposta.aceita.matchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class MatchServiceApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));

		SpringApplication.run(MatchServiceApplication.class, args);
	}

}
