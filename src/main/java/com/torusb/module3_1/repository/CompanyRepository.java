package com.torusb.module3_1.repository;

import com.torusb.module3_1.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
