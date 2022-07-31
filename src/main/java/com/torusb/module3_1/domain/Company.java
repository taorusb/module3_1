package com.torusb.module3_1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@DynamicUpdate
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "symbol")
	private String symbol;
	@Column(name = "exchange")
	private String exchange;
	@Column(name = "exchangeName")
	private String exchangeName;
	@Column(name = "exchange_segment")
	private String exchangeSegment;
	@Column(name = "exchange_suffix")
	private String exchangeSuffix;
	@Column(name = "exchange_segment_name")
	private String exchangeSegmentName;
	@Column(name = "name")
	private String name;
	@Column(name = "date")
	private LocalDate date;
	@Column(name = "type")
	private String type;
	@Column(name = "iex_id")
	private String iexId;
	@Column(name = "region")
	private String region;
	@Column(name = "currency")
	private String currency;
	@Column(name = "is_enabled")
	private Boolean isEnabled;
	@Column(name = "figi")
	private String figi;
	@Column(name = "cik")
	private String cik;
	@Column(name = "lei")
	private String lei;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		Company company = (Company) o;
		return id != null && Objects.equals(id, company.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
