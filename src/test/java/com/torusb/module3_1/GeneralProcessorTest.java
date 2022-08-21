package com.torusb.module3_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.torusb.module3_1.domain.Company;
import com.torusb.module3_1.domain.Stock;
import com.torusb.module3_1.processor.GeneralProcessor;
import com.torusb.module3_1.processor.ResultHolder;
import com.torusb.module3_1.service.CompanyService;
import com.torusb.module3_1.service.IexapisService;
import com.torusb.module3_1.service.LogService;
import com.torusb.module3_1.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeneralProcessorTest {

	GeneralProcessor generalProcessor;
	@Mock
	StockService stockService;
	@Mock
	LogService logService;
	@Mock
	IexapisService iexapisService;
	@Mock
	CompanyService companyService;
	ResultHolder resultHolder = new ResultHolder(new ObjectMapper());
	ExecutorService executorService = Executors.newFixedThreadPool(8);
	PrintWriter printWriter = new PrintWriter(System.out, true);
	Map<String, Stock> stockMap;
	Map<String, Stock> persistedStocksMap;
	List<Company> companyList;

	@BeforeEach
	void setup() throws Exception {
		companyList = EntityGenerator.getCompanies();
		generalProcessor =
				new GeneralProcessor(stockService, logService, iexapisService,
						printWriter, new CurrentThreadExecutor(), executorService, companyService, resultHolder);
		when(iexapisService.getCompanies()).thenReturn(companyList);
		stockMap = EntityGenerator.getRawStockPack()
				.stream().collect(Collectors.toMap(Stock::getCompanyName, Function.identity()));
		persistedStocksMap = EntityGenerator.getPersistedStockPack()
				.stream().collect(Collectors.toMap(Stock::getCompanyName, Function.identity()));
		when(iexapisService.getSingleStock(Mockito.anyString()))
				.thenAnswer(i -> stockMap.get(i.getArgument(0)));
		when(stockService.getChangedValue()).thenReturn(EntityGenerator.getChangedValues());
		when(companyService.saveAll(Mockito.anyList())).thenReturn(companyList);
	}

	@Test
	void process() {
		assertDoesNotThrow(() -> generalProcessor.process());
	}

	private class CurrentThreadExecutor implements Executor {
		public void execute(Runnable r) {
			r.run();
		}
	}
}