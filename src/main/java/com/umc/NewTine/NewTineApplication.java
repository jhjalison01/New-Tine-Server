package com.umc.NewTine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class NewTineApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewTineApplication.class, args);
	}

}
