package com.upakb.brainserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

// Exclude the DataSource auto-config here
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BrainservApplication {
	public static void main(String[] args) {
		SpringApplication.run(BrainservApplication.class, args);
	}
}
