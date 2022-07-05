package com.torusb.module3_1.service;

import com.torusb.module3_1.domain.LogRecord;
import com.torusb.module3_1.domain.Stock;
import com.torusb.module3_1.repository.LogRepository;
import com.torusb.module3_1.repository.StockRepository;
import com.torusb.module3_1.utils.StockLoggingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Log4j2
public class LogService {

	private final LogRepository logRepository;
	private final StockRepository stockRepository;

	public void logChanges(List<Stock> stocks) {
		log.debug("IN logChanges : saving logs, count is {}", stocks.size());
		List<Stock> previous = stockRepository.findAll();
		if (!previous.isEmpty()) {
			List<LogRecord> records = StockLoggingUtils.getLogs(stocks, previous);
			logRepository.saveAll(records);
		}
	}
}