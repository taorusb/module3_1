package com.torusb.module3_1;

import com.torusb.module3_1.domain.LogRecord;
import com.torusb.module3_1.domain.Stock;
import com.torusb.module3_1.utils.StockLoggingUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class StockLoggingUtilsTest {

	List<Stock> previous = EntityGenerator.getPersistedStockPack();
	List<Stock> current = EntityGenerator.getRawStockPack();

	@Test
	void getLogs() {
		List<LogRecord> records = StockLoggingUtils.getLogs(current, previous);
		Assertions.assertEquals(5, StockLoggingUtils.getLogs(current, previous).size());
	}
}