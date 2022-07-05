package com.torusb.module3_1.config;

import com.torusb.module3_1.processor.GeneralProcessor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
@EnableScheduling
@AllArgsConstructor
public class Config {

	private final GeneralProcessor processor;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ExecutorService executorService(@Value("executor.threads") int nThreads) {
		return Executors.newFixedThreadPool(nThreads);
	}

	@Scheduled(fixedDelay = 5000)
	public void scheduleFixedDelayTask() {
		processor.process();
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
