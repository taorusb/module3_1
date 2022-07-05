package com.torusb.module3_1.utils;

import com.torusb.module3_1.domain.LogRecord;
import com.torusb.module3_1.domain.Stock;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.util.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StockLoggingUtils {

	private StockLoggingUtils() { }

	public static List<LogRecord> getLogs(List<Stock> current, List<Stock> previous) {
		Map<String, Stock> stockMap = fillMap(previous);
		return current.stream()
				.filter(stock -> stockMap.containsKey(stock.getCompanyName()))
				.map(stock -> Pair.of(stock, stockMap.get(stock.getCompanyName())))
				.map(StockLoggingUtils::getRecords)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	private static Map<String, Stock> fillMap(List<Stock> stocks) {
		return stocks.stream().collect(Collectors.toMap(Stock::getCompanyName, Function.identity()));
	}

	private static List<LogRecord> getRecords(Pair<Stock, Stock> pair) {
		Stock current = pair.getFirst();
		Stock previous = pair.getSecond();
		return getRecords(current, previous);
	}

	private static List<LogRecord> getRecords(Stock current, Stock previous) {
		return getRecordStream(current, previous)
				.filter(s -> !s.isEmpty())
				.map(s -> {
					LogRecord record = new LogRecord();
					record.setRecord(s);
					return record;
				})
				.collect(Collectors.toList());
	}

	private static Stream<String> getRecordStream(Stock current, Stock previous) {
		String s1 = getCurrencyRecord(current, previous);
		String s2 = getVolumeRecord(current, previous);
		String s3 = getLatestPriceRecord(current, previous);
		return Stream.of(s1, s2, s3);
	}

	private static String getCurrencyRecord(Stock current, Stock previous) {
		String company = current.getCompanyName();
		String s1 = current.getCurrency();
		String s2 = previous.getCurrency();
		if (!s1.equals(s2)) {
			return createRecord(RecordType.CURRENCY, company, s2, s1);
		}
		return Strings.EMPTY;
	}

	private static String getVolumeRecord(Stock current, Stock previous) {
		String company = current.getCompanyName();
		String volume1 = current.getVolume().toString();
		String volume2 = previous.getVolume().toString();
		if (!volume1.equals(volume2)) {
			return createRecord(RecordType.VOLUME, company, volume2, volume1);
		}
		return Strings.EMPTY;
	}

	private static String getLatestPriceRecord(Stock current, Stock previous) {
		String company = current.getCompanyName();
		String price1 = current.getLatestPrice().toString();
		String price2 = previous.getLatestPrice().toString();
		if (!price1.equals(price2)) {
			return createRecord(RecordType.LATEST_PRICE, company, price2, price1);
		}
		return Strings.EMPTY;
	}

	private static String createRecord(RecordType type, String company, String from, String to) {
		switch (type) {
			case LATEST_PRICE:
				return String.format(RecordType.LATEST_PRICE.value, company, from, to);
			case CURRENCY:
				return String.format(RecordType.CURRENCY.value, company, from, to);
			case VOLUME:
				return String.format(RecordType.VOLUME.value, company, from, to);
			default:
				throw new IllegalArgumentException("Wrong type " + type);
		}
	}
}
