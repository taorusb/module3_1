package com.torusb.module3_1.processor;

import com.torusb.module3_1.domain.Company;
import com.torusb.module3_1.domain.Stock;
import com.torusb.module3_1.service.CompanyService;
import com.torusb.module3_1.service.LogService;
import com.torusb.module3_1.service.IexapisService;
import com.torusb.module3_1.service.StockService;
import com.torusb.module3_1.utils.StockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Log4j2
public class GeneralProcessor {

	private final StockService stockService;
	private final LogService logService;
	private final IexapisService iexapisService;
	private final PrintWriter printWriter;
	private final Executor executor;
	private final ExecutorService executorService;
	private final CompanyService companyService;
	private final ResultHolder resultHolder;
	private final BlockingQueue<String> queue = new PriorityBlockingQueue<>();
	private static final String changeValueTemplate = "|%30s|%10s|%15s|%15s|%15s|";
	private static final String topValueTemplate = "|%30s|%10s|%15s|%15s|";
	private static final String[] values = {"company", "currency", "volume", "latestPrice"};

	public void process() {
		log.debug("IN process : starting executing app");
		CompletableFuture.supplyAsync(iexapisService::getCompanies, executor)
				.thenApply(this::saveCompanies)
				.thenApply(this::getEnabledCompaniesSymbols)
				.thenAccept(this::pushToQueue)
				.thenApply(this::handleQueue)
				.thenApply(this::printResult)
				.thenApply(this::writeLogToDb)
				.thenApply(this::saveResult);
	}

	private List<Company> saveCompanies(List<Company> companies) {
		log.debug("IN saveAll : saving companies, count is {}", companies.size());
		return companyService.saveAll(companies);
	}

	private List<String> getEnabledCompaniesSymbols(List<Company> companies) {
		return companies.stream()
				.filter(Company::getIsEnabled)
				.map(Company::getSymbol)
				.collect(Collectors.toList());
	}

	private void pushToQueue(List<String> enabledCompanies) {
		log.debug("IN pushToQueue : pushing to queue enabled companies witch size is " + enabledCompanies.size());
		enabledCompanies.forEach(name -> {
			try {
				queue.put(name);
			} catch (InterruptedException e) {
				log.error("IN pushToQueue : something went wrong {}", e.getMessage());
				e.printStackTrace();
			}
		});
	}

	private List<Stock> handleQueue(Void unused) {
		log.debug("IN handleQueue : handling queue, the size is {}", queue.size());
		List<Callable<Stock>> tasks = new ArrayList<>();
		while (!queue.isEmpty()) {
			tasks.add(getTask(queue.poll()));
		}
		return executeTasks(tasks);
	}

	private List<Stock> executeTasks(List<Callable<Stock>> tasks) {
		try {
			List<Future<Stock>> futures = executorService.invokeAll(tasks);
			log.debug("IN executeTasks : all tasks are executed, count is {}", futures.size());
			return futures.stream().filter(Future::isDone).map(future -> {
				try {
					return future.get();
				} catch (InterruptedException | ExecutionException e) {
					log.error("IN executeTasks : something went wrong {}", e.getMessage());
					throw new IllegalArgumentException(e);
				}
			}).collect(Collectors.toList());
		} catch (InterruptedException e) {
			log.error("IN executeTasks : something went wrong {}", e.getMessage());
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}
		return Collections.emptyList();
	}

	private Callable<Stock> getTask(String symbol) {
		return () -> iexapisService.getSingleStock(symbol);
	}

	private List<Stock> printResult(List<Stock> stocks) {
		log.info("IN printResult : printing a result");
		List<Pair<String, Integer>> changedValues = stockService.getChangedValue();
		List<Stock> topValueStocks = StockUtils.getHighestValueStocks(stocks);
		resultHolder.setTopValueStocks(topValueStocks);
		printTopValue(topValueStocks);
		if (!changedValues.isEmpty()) {
			List<Stock> changedValueStocks = StockUtils.getChangedValueStocks(stocks, changedValues);
			resultHolder.setChangedValueStocks(changedValueStocks);
			printChangedValues(changedValueStocks);
		}
		return stocks;
	}

	private void printTopValue(List<Stock> stocks) {
		printWriter.println("____________________________top volume stocks_____________________________");
		printWriter.printf((topValueTemplate) + "%n", values[0], values[1], values[2], values[3]);
		stocks.forEach(stock ->
			printWriter.printf((topValueTemplate) + "%n", stock.getCompanyName(), stock.getCurrency(),
					stock.getVolume(), stock.getLatestPrice())
		);
		printWriter.print("\n");
	}

	private void printChangedValues(List<Stock> stocks) {
		printWriter.println("______________________________________top changed stocks__________________________________");
		printWriter.printf((changeValueTemplate) + "%n", values[0], values[1], values[2], values[3], "changeValue");
		stocks.forEach(stock ->
			printWriter.printf((changeValueTemplate) + "%n", stock.getCompanyName(),
					stock.getCurrency(), stock.getVolume(), stock.getLatestPrice(), stock.getChangeValue())
		);
		printWriter.print("\n");
	}

	private List<Stock> writeLogToDb(List<Stock> stocks) {
		log.debug("IN writeLogToDb : logging changes");
		logService.logChanges(stocks);
		return stocks;
	}

	private List<Stock> saveResult(List<Stock> stocks) {
		log.debug("IN saveResult : saving the result");
		return stockService.saveAll(stocks);
	}
}
