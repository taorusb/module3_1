package com.torusb.module3_1.controller;

import com.torusb.module3_1.processor.ResultHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ApplicationController {

	private final ResultHolder resultHolder;

	@GetMapping("/top_values")
	public ResponseEntity getTopValuesStocks() {
		Optional<String> stocks = Optional.of(resultHolder.getTopValueStocks());
		return ResponseEntity.of(stocks);
	}

	@GetMapping("/changed_values")
	public ResponseEntity getChangedValueStocks() {
		Optional<String> stocks = Optional.of(resultHolder.getChangedValueStocks());
		return ResponseEntity.of(stocks);
	}
}