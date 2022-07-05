package com.torusb.module3_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.torusb.module3_1.domain.Company;
import com.torusb.module3_1.domain.Stock;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntityGenerator {

	private static final List<Stock> rawStocks = List.of(
			new Stock("one", "usd", 15, null, 15, null),
			new Stock("two", "usd", 15, null, 15, null),
			new Stock("three", "usd", 14, null, 15, null),
			new Stock("four", "usd", 13, null, 15, null),
			new Stock("five", "usd", 12, null, 15, null),
			new Stock("six", "usd", 10, null, 15, null),
			new Stock("seven", "usd", 10, null, 15, null),
			new Stock("eight", "usd", 10, null, 15, null),
			new Stock("nine", "usd", 10, null, 15, null),
			new Stock("ten", "usd", 10, null, 15, null)
	);

	private static final List<Stock> persistedStocks = List.of(
			new Stock("one", "usd", 15, null, 15, null),
			new Stock("two", "usd", 15, null, 15, null),
			new Stock("three", "usd", 14, null, 15, null),
			new Stock("four", "usd", 13, null, 15, null),
			new Stock("five", "usd", 12, null, 15, null),
			new Stock("six", "usd", 10, null, 9, null),
			new Stock("seven", "usd", 10, null, 8, null),
			new Stock("eight", "usd", 10, null, 7, null),
			new Stock("nine", "usd", 10, null, 6, null),
			new Stock("ten", "usd", 10, null, 5, null)
	);

	private static final List<Pair<String, Integer>> changedValues = List.of(
			Pair.of("ten", 5), Pair.of("nine", 6), Pair.of("eight", 7), Pair.of("seven", 8), Pair.of("six", 9)
	);
	private EntityGenerator() { }

 	public static List<Stock> getPersistedStockPack() {
		return persistedStocks;
	}

	public static List<Stock> getRawStockPack() {
		return rawStocks;
	}

	public static List<Pair<String, Integer>> getChangedValues() {
		return changedValues;
	}

	public static List<Company> getCompanies() {
		ObjectMapper ob = new ObjectMapper()
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
		URL url = ClassLoader.getSystemClassLoader().getResource("companies.json");
		assertNotNull(url);
		Company[] objects;
		try {
			objects = ob.readValue(Path.of(url.toURI()).toFile(), Company[].class);
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
		List<Company> companyList = Arrays.stream(objects).collect(Collectors.toList());
		assertNotNull(objects);
		return companyList;
	}


}
