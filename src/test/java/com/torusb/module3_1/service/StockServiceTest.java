package com.torusb.module3_1.service;

import com.torusb.module3_1.EntityGenerator;
import com.torusb.module3_1.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class StockServiceTest {

	@Mock
	StockRepository stockRepository;
	StockService stockService;
	List<Object[]> values = EntityGenerator.getChangedValues()
			.stream().map(this::getValue).collect(Collectors.toList());

	@BeforeEach
	void setup() {
		stockService = new StockService(stockRepository);
	}

	private Object[] getValue(Pair<String, Integer> pair) {
		return new Object[] {pair.getFirst(), pair.getSecond()};
	}

	@Test
	void saveAll() {
		Mockito.when(stockRepository.saveAll(Mockito.any())).thenReturn(EntityGenerator.getPersistedStockPack());
		assertEquals(10, stockService.saveAll(EntityGenerator.getRawStockPack()).size());
	}

	@Test
	void getChangedValue() {
		Mockito.when(stockRepository.getStocksChangeValue()).thenReturn(values);
		assertEquals(5, stockService.getChangedValue().size());
	}
}