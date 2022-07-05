package com.torusb.module3_1.service;

import com.torusb.module3_1.domain.Stock;
import com.torusb.module3_1.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log4j2
public class StockService {

	private final StockRepository stockRepository;
	private static final int COMPANY_NAME_INDEX = 0;
	private static final int CHANGED_VALUE_INDEX = 1;

	public List<Stock> saveAll(List<Stock> stocks) {
		log.debug("IN saveAll : saving stocks, count is {}", stocks.size());
		return stockRepository.saveAll(stocks);
	}

	public List<Pair<String, Integer>> getChangedValue() {
		log.debug("IN getChangedValue : getting changed values from repo");
		List<Pair<String, Integer>> result = stockRepository.getStocksChangeValue().stream()
				.map(this::getPair)
				.collect(Collectors.toList());
		if (!result.isEmpty()) {
			return result;
		}
		return Collections.emptyList();
	}

	private Pair<String, Integer> getPair(Object[] rawValues) {
		String name = (String) rawValues[COMPANY_NAME_INDEX];
		Integer value = (Integer) rawValues[CHANGED_VALUE_INDEX];
		return Pair.of(name, value);
	}
}
