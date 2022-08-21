package com.torusb.module3_1.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torusb.module3_1.domain.Stock;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ResultHolder {

	private List<Stock> topValueStocks = new ArrayList<>();

	private List<Stock> changedValueStocks = new ArrayList<>();

	private final ObjectMapper objectMapper;

	public String getTopValueStocks() {
		try {
			return objectMapper.writeValueAsString(topValueStocks);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public void setTopValueStocks(List<Stock> topValueStocks) {
		this.topValueStocks = topValueStocks;
	}

	public String getChangedValueStocks() {
		try {
			return objectMapper.writeValueAsString(changedValueStocks);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public void setChangedValueStocks(List<Stock> changedValueStocks) {
		this.changedValueStocks = changedValueStocks;
	}
}
