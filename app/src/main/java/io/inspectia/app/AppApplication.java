package io.inspectia.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class AppApplication {


	public static void main(String[] args) throws ExecutionException, InterruptedException {
		SpringApplication.run(AppApplication.class, args);
	}

}
