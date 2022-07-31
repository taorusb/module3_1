package com.torusb.module3_1.config;

import com.torusb.module3_1.processor.GeneralProcessor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
@Configuration
@EnableScheduling
public class Scheduler {

	private final GeneralProcessor processor;

	@Scheduled(fixedDelay = 5000)
	public void scheduleFixedDelayTask() {
		processor.process();
	}
}
