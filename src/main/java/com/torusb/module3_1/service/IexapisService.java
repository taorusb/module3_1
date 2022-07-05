package com.torusb.module3_1.service;

import com.torusb.module3_1.domain.Company;
import com.torusb.module3_1.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log4j2
public class IexapisService {

	private static String TOKEN;
	private static String URL_ALL;
	private static String URL_SINGLE;
	private final RestTemplate restTemplate;

	public List<Company> getCompanies() {
		log.debug("IN getCompanies : trying to get companies");
		Company[] companies = restTemplate.getForEntity(String.format(URL_ALL, TOKEN), Company[].class).getBody();
		log.debug("IN getCompanies : received companies witch size is {}", companies != null ? companies.length : 0);
		return Optional.ofNullable(companies)
				.map(company -> Arrays.stream(company).collect(Collectors.toList())).orElse(Collections.emptyList());
	}

	public Stock getSingleStock(String symbol) {
		log.debug("IN getSingleStock : trying to get company with symbol {}", symbol);
		return restTemplate.getForEntity(String.format(URL_SINGLE, symbol, TOKEN), Stock.class).getBody();
	}

	@Value("${iexapis.token}")
	public void setToken(String token){
		IexapisService.TOKEN = token;
	}

	@Value("${iexapis.url.all}")
	public void setUrlAll(String urlAll){
		IexapisService.URL_ALL = urlAll;
	}

	@Value("${iexapis.url.single}")
	public void setUrlSingle(String urlSingle){
		IexapisService.URL_SINGLE = urlSingle;
	}
}
