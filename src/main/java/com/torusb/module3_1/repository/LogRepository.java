package com.torusb.module3_1.repository;

import com.torusb.module3_1.domain.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogRecord, Long> {
}
