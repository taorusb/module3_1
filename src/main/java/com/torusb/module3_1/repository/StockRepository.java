package com.torusb.module3_1.repository;

import com.torusb.module3_1.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

	@Query(value = "select spd.company_name, spd.change_value from stock_price_delta spd", nativeQuery = true)
	List<Object[]> getStocksChangeValue();
}
