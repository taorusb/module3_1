package com.torusb.module3_1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
@EnableScheduling
public class Config {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ExecutorService executorService(@Value("${executor.threads}") int nThreads) {
		return Executors.newFixedThreadPool(nThreads);
	}

	@Bean
	public PrintWriter printWriter() {
		return new PrintWriter(System.out, true);
	}

	@Bean
	public Executor executor() {
		return Thread::new;
	}
}
