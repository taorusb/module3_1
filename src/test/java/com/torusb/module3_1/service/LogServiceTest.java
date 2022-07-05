package com.torusb.module3_1.service;

import com.torusb.module3_1.EntityGenerator;
import com.torusb.module3_1.repository.LogRepository;
import com.torusb.module3_1.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

	@Mock
	LogRepository logRepository;
	@Mock
	StockRepository stockRepository;
	LogService logService;

	@Test
	void logChanges() {
		logService = new LogService(logRepository, stockRepository);
		assertDoesNotThrow(() -> logService.logChanges(EntityGenerator.getRawStockPack()));
	}
}