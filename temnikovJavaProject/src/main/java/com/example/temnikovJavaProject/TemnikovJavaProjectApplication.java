package com.example.temnikovJavaProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TemnikovJavaProjectApplication {

	public static void main(String[] args) {
		ConfigManager configReader = new ConfigManager();
		try {
			configReader.readConfigFile("src/main/java/com/example/temnikovJavaProject/config.cfg");
		} catch (IOException e) {
			e.printStackTrace();
		}

		SpringApplication.run(TemnikovJavaProjectApplication.class, args);

	}

}
