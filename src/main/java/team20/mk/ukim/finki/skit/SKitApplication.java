package team20.mk.ukim.finki.skit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SKitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SKitApplication.class, args);
	}

}
