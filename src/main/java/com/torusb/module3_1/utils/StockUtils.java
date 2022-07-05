package com.torusb.module3_1.utils;

import com.torusb.module3_1.domain.Stock;
import org.springframework.data.util.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StockUtils {

	private StockUtils() { }

	public static List<Stock> getHighestValueStocks(List<Stock> stocks) {
		return stocks.stream()
				.sorted(new StockUtils.HighestValueComparator().reversed())
				.limit(5L)
				.collect(Collectors.toList());
	}

	private static class HighestValueComparator implements Comparator<Stock> {
		@Override
		public int compare(Stock s1, Stock s2) {
			Integer s1Value = s1.getVolume();
			Integer s2Value = s2.getVolume();
			String s1Name = s1.getCompanyName();
			String s2Name = s2.getCompanyName();
			if (s1Value > s2Value) {
				return 1;
			} else if (s1Value.equals(s2Value)) {
				return s2Name.compareTo(s1Name);
			} else {
				return -1;
			}
		}
	}

	public static List<Stock> getChangedValueStocks(List<Stock> current, List<Pair<String, Integer>> previous) {
		Map<String, Integer> previousStocksChangedValue = previous.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
		return current.stream()
				.filter(stock -> previousStocksChangedValue.containsKey(stock.getCompanyName()))
				.map(stock -> mapPair(stock, previousStocksChangedValue.get(stock.getCompanyName())))
				.map(StockUtils::setCalculatedValue)
				.sorted(new StockUtils.ChangeValueComparator().reversed())
				.limit(5L)
				.collect(Collectors.toList());
	}

	private static Pair<Stock, Integer> mapPair(Stock stock, Integer integer) {
		return Pair.of(stock, integer);
	}

	private static Stock setCalculatedValue(Pair<Stock, Integer> pair) {
		Stock stock = pair.getFirst();
		Integer changedValue = stock.getLatestPrice() - pair.getSecond();
		stock.setChangeValue(changedValue);
		return stock;
	}

	private static class ChangeValueComparator implements Comparator<Stock> {
		@Override
		public int compare(Stock o1, Stock o2) {
			return o1.getChangeValue() - o2.getChangeValue();
		}
	}

	private static Map<String, Stock> fillMap(List<Stock> stocks) {
		 return stocks.stream().collect(Collectors.toMap(Stock::getCompanyName, Function.identity()));
	}
}
