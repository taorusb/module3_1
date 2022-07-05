package com.torusb.module3_1.service;

import com.torusb.module3_1.EntityGenerator;
import com.torusb.module3_1.domain.Company;
import com.torusb.module3_1.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

	@Mock
	CompanyRepository companyRepository;

	@Test
	void saveAll() throws Exception {
		CompanyService companyService = new CompanyService(companyRepository);
		assertNotNull(companyService);

		List<Company> companies = EntityGenerator.getCompanies();
		Mockito.when(companyRepository.saveAll(companies)).thenReturn(companies);
		assertNotNull(companyService.saveAll(companies));
		assertEquals(10, companyService.saveAll(companies).size());
	}
}