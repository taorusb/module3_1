package com.torusb.module3_1.service;

import com.torusb.module3_1.domain.Company;
import com.torusb.module3_1.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CompanyService {

	private final CompanyRepository companyRepository;

	public List<Company> saveAll(List<Company> companies) {
		return companyRepository.saveAll(companies);
	}
}
