package com.torusb.module3_1;

import com.torusb.module3_1.domain.Stock;
import com.torusb.module3_1.utils.StockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StockUtilsTest {

	List<Stock> previous = EntityGenerator.getPersistedStockPack();
	List<Stock> current = EntityGenerator.getRawStockPack();
	List<Pair<String, Integer>> changedValues =
			previous.stream()
					.map(stock -> Pair.of(stock.getCompanyName(), stock.getLatestPrice()))
					.collect(Collectors.toList());

	@Test
	void getHighestValueStocks() {
		List<Stock> expected = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			expected.add(current.get(i));
		}
		List<Stock> result = StockUtils.getHighestValueStocks(current);
		assertEquals(5, result.size());
		for (int i = 0; i < 5; i++) {
			assertEquals(expected.get(i), result.get(i));
		}
	}

	@Test
	void getChangedValueStocks() {
		List<Stock> result = StockUtils.getChangedValueStocks(current, changedValues);
		assertEquals(5, result.size());
		int value = 180;
		for (int i = result.size() - 1; i >= 5; i--) {
			Stock stock = result.get(i);
			assertEquals(value, stock.getChangeValue());
			value = value - 20;
		}
	}
}