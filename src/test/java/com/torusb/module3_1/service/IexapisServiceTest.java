package com.torusb.module3_1.service;

import com.torusb.module3_1.EntityGenerator;
import com.torusb.module3_1.domain.Company;
import com.torusb.module3_1.domain.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IexapisServiceTest {

	@Mock
	RestTemplate restTemplate;
	@Mock
	ResponseEntity entity;
	IexapisService iexapisService;
	Stock stock = EntityGenerator.getRawStockPack().get(0);
	Company[] companies = EntityGenerator.getCompanies().toArray(Company[]::new);

	@BeforeEach
	void setup() throws Exception {
		iexapisService = new IexapisService(restTemplate);
		iexapisService.setToken("token");
		iexapisService.setUrlAll("url_all/");
		iexapisService.setUrlSingle("url_single/");
	}

	@Test
	void getCompanies() {
		Mockito.when(restTemplate.getForEntity("url_all/", Company[].class)).thenReturn(
				(ResponseEntity<Company[]>) entity);
		Mockito.when(entity.getBody()).thenReturn(companies);

		assertEquals(10, iexapisService.getCompanies().size());
	}

	@Test
	void getSingleStock() {
		Mockito.when(restTemplate.getForEntity("url_single/", Stock.class)).thenReturn((ResponseEntity<Stock>) entity);
		Mockito.when(entity.getBody()).thenReturn(stock);

		assertEquals(stock, iexapisService.getSingleStock("symbol"));
	}
}